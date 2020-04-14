package com.carryxyh.check;

import com.carryxyh.CacheClient;
import com.carryxyh.CheckResult;
import com.carryxyh.Checker;
import com.carryxyh.KeysInput;
import com.carryxyh.TempDataDB;
import com.carryxyh.config.CheckerConfig;
import com.carryxyh.config.Config;
import com.carryxyh.constants.ValueType;
import com.carryxyh.lifecycle.Endpoint;
import com.carryxyh.mix.NamedThreadFactory;
import com.carryxyh.mix.ThreadPerTaskExecutor;
import com.carryxyh.tempdata.ConflictResultData;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import javafx.util.Pair;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/**
 * AbstractChecker
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public abstract class AbstractChecker<C extends CacheClient>
        extends Endpoint implements Checker<C> {

    // private field.

    private final Executor executor =
            new ThreadPerTaskExecutor(new NamedThreadFactory("checker-", true));

    private int parallel;

    private int rounds;

    private long internal;

    // protected field.

    protected C source;

    protected C target;

    protected TempDataDB tempDataDB;

    protected AbstractChecker(TempDataDB tempDataDB, C source, C target) {
        this.tempDataDB = tempDataDB;
        this.source = source;
        this.target = target;
    }

    @Override
    public List<ConflictResultData> check(KeysInput input) {
        List<String> keys = input.input();
        if (CollectionUtils.isEmpty(keys)) {
            return Lists.newArrayList();
        }

        final Map<Integer, List<Pair<String, String>>> hashed = Maps.newHashMap();
        for (String k : keys) {
            int i = k.hashCode();
            int h = i % parallel;
            List<Pair<String, String>> strings = hashed.computeIfAbsent(h, integer -> Lists.newArrayList());
            strings.add(new Pair<>(k, null));
        }

        final CountDownLatch countDownLatch = new CountDownLatch(parallel);
        for (int x = 0; x < parallel; x++) {
            final int tempParallel = x;
            executor.execute(() -> {
                List<Pair<String, String>> needDiff = hashed.get(tempParallel);
                List<ConflictResultData> tempData = doCheck(needDiff);
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
                    List<ConflictResultData> load = tempDataDB.load(generateKey(tempRound, tempParallel));
                    if (CollectionUtils.isNotEmpty(load)) {

                        List<ConflictResultData> tempData = doCheck(load.
                                stream().
                                map(conflictResultData ->
                                        new Pair<>(conflictResultData.getKey(), conflictResultData.getFieldOrSubKey())).
                                collect(Collectors.toList()));
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
        List<ConflictResultData> result = Lists.newArrayList();
        for (int i = 0; i < parallel; i++) {
            List<ConflictResultData> load = tempDataDB.load(generateKey(rounds - 1, parallel));
            result.addAll(load);
        }
        return result;
    }

    protected String generateKey(int round, int parallel) {
        return round + "-" + parallel;
    }

    protected ConflictResultData toTempData(CheckResult check,
                                            String key,
                                            String subKey,
                                            Object sourceValue,
                                            Object targetValue) {
        ConflictResultData t = new ConflictResultData();
        t.setConflictType(check.getConflictType().getType());
        t.setKey(key);
        t.setFieldOrSubKey(subKey);
        t.setSourceValue(sourceValue);
        t.setTargetValue(targetValue);
        t.setValueType(ValueType.MEMCACHE.getType());
        return t;
    }

    protected abstract List<ConflictResultData> doCheck(List<Pair<String, String>> keys);

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
    public C source() {
        return source;
    }

    @Override
    public C target() {
        return target;
    }
}
