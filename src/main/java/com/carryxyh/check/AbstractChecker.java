package com.carryxyh.check;

import com.carryxyh.CacheClient;
import com.carryxyh.CheckStrategy;
import com.carryxyh.Checker;
import com.carryxyh.KeysInput;
import com.carryxyh.TempData;
import com.carryxyh.TempDataDB;
import com.carryxyh.config.CheckerConfig;
import com.carryxyh.config.Config;
import com.carryxyh.lifecycle.Endpoint;
import com.carryxyh.mix.NamedThreadFactory;
import com.carryxyh.mix.ThreadPerTaskExecutor;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * AbstractChecker
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public abstract class AbstractChecker<S extends CacheClient, T extends CacheClient>
        extends Endpoint implements Checker<S, T> {

    // private field.

    private final Executor executor =
            new ThreadPerTaskExecutor(new NamedThreadFactory("checker-", true));

    private int parallel;

    private int rounds;

    private long internal;

    // protected field.

    protected CheckStrategy checkStrategy;

    protected S source;

    protected T target;

    protected TempDataDB tempDataDB;

    protected AbstractChecker(TempDataDB tempDataDB, S source, T target) {
        this.tempDataDB = tempDataDB;
        this.source = source;
        this.target = target;
    }

    @Override
    public List<TempData> check(KeysInput input) {
        List<String> keys = input.input();
        if (CollectionUtils.isEmpty(keys)) {
            return Lists.newArrayList();
        }

        final Map<Integer, List<String>> hashed = Maps.newHashMap();
        for (String k : keys) {
            int i = k.hashCode();
            int h = i % parallel;
            List<String> strings = hashed.computeIfAbsent(h, integer -> Lists.newArrayList());
            strings.add(k);
        }

        final CountDownLatch countDownLatch = new CountDownLatch(parallel);
        for (int x = 0; x < parallel; x++) {
            final int tempParallel = x;
            executor.execute(() -> {
                List<String> needDiff = hashed.get(tempParallel);
                List<TempData> tempData = firstCheck(needDiff);
                if (CollectionUtils.isNotEmpty(tempData)) {
                    tempDataDB.save(generateKey(0, tempParallel), tempData);
                }
                countDownLatch.countDown();
            });
        }

        try {
            countDownLatch.await();
            Thread.sleep(internal);
        } catch (InterruptedException ignored) {
        }

        for (int i = 1; i < rounds; i++) {
            final int tempRound = i;
            final CountDownLatch c = new CountDownLatch(parallel);
            for (int x = 0; x < parallel; x++) {
                final int tempParallel = x;
                executor.execute(() -> {
                    List<TempData> load = tempDataDB.load(generateKey(tempRound, tempParallel));
                    if (CollectionUtils.isNotEmpty(load)) {
                        List<TempData> tempData = roundCheck(load);
                        if (CollectionUtils.isNotEmpty(tempData)) {
                            tempDataDB.save(generateKey(tempRound, tempParallel), tempData);
                        }
                    }
                    c.countDown();
                });
            }

            try {
                c.await();
                Thread.sleep(internal);
            } catch (InterruptedException ignored) {
            }
        }

        // last rounds result as final result.
        List<TempData> result = Lists.newArrayList();
        for (int i = 0; i < parallel; i++) {
            List<TempData> load = tempDataDB.load(generateKey(rounds - 1, parallel));
            result.addAll(load);
        }
        return result;
    }

    protected String generateKey(int round, int parallel) {
        return round + "-" + parallel;
    }

    protected abstract List<TempData> firstCheck(List<String> keys);

    protected abstract List<TempData> roundCheck(List<TempData> tempData);

    @Override
    protected void doInit(Config config) throws Exception {
        CheckerConfig checkerConfig = (CheckerConfig) config;
        int parallel = checkerConfig.getParallel();
        if (parallel <= 0) {
            throw new IllegalArgumentException("parallel");
        }
        this.parallel = parallel;

        int rounds = checkerConfig.getRounds();
        if (rounds <= 0) {
            throw new IllegalArgumentException("rounds");
        }
        this.rounds = rounds;

        long internal = checkerConfig.getInternal();
        if (internal <= 0) {
            throw new IllegalArgumentException("internal");
        }
        this.internal = internal;
    }

    @Override
    protected void doStart() throws Exception {
        this.tempDataDB.start();
        this.source.start();
        this.target.start();
    }

    @Override
    protected void doClose() throws Exception {
        this.tempDataDB.close();
        this.source.close();
        this.target.close();
    }

    @Override
    protected void doStop() throws Exception {
        this.tempDataDB.stop();
        this.source.stop();
        this.target.stop();
    }

    @Override
    public S source() {
        return source;
    }

    @Override
    public T target() {
        return target;
    }
}
