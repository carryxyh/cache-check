package com.carryxyh.check;

import com.carryxyh.CacheClient;
import com.carryxyh.CheckStrategy;
import com.carryxyh.Checker;
import com.carryxyh.CheckerConfig;
import com.carryxyh.KeysInput;
import com.carryxyh.lifecycle.Endpoint;
import com.carryxyh.mix.NamedThreadFactory;
import com.carryxyh.mix.ThreadPerTaskExecutor;
import com.carryxyh.TempDataDB;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

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
public abstract class AbstractChecker<SOURCE extends CacheClient, TARGET extends CacheClient> extends Endpoint implements Checker<SOURCE, TARGET> {

    protected final CheckerConfig checkerConfig;

    protected TempDataDB tempDataDB;

    private final Executor executor = new ThreadPerTaskExecutor(new NamedThreadFactory("checker-", true));

    private CheckStrategy checkStrategy;

    protected AbstractChecker(CheckerConfig checkerConfig) {
        this.checkerConfig = checkerConfig;
    }

    @Override
    public void check(KeysInput input) {
        List<String> keys = input.input();
        if (keys == null || keys.size() == 0) {
            return;
        }

        int parallel = checkerConfig.getParallel();
        int rounds = checkerConfig.getRounds();
        long internal = checkerConfig.getInternal();


        final Map<Integer, List<String>> hashed = Maps.newHashMap();
        for (String k : keys) {
            int i = k.hashCode();
            int h = i % parallel;
            List<String> strings = hashed.computeIfAbsent(h, integer -> Lists.newArrayList());
            strings.add(k);
        }

        for (int i = 0; i < rounds; i++) {
            final int tempRound = i;
            final CountDownLatch countDownLatch = new CountDownLatch(parallel);
            for (int x = 0; x < parallel; x++) {
                final int temp = x;
                executor.execute(() -> {
                    List<String> needDiff = hashed.get(temp);
                    doCheck(needDiff, tempRound, countDownLatch, checkStrategy);
                });
            }

            try {
                countDownLatch.await();
                Thread.sleep(internal);
            } catch (InterruptedException ignored) {
            }
        }
    }

    protected abstract void doCheck(List<String> keys,
                                    int round,
                                    CountDownLatch countDownLatch,
                                    CheckStrategy checkStrategy);

    @Override
    protected void doInit() throws Exception {
        this.tempDataDB = checkerConfig.buildTempDataDB();
        this.checkStrategy = checkerConfig.getCheckStrategy();
    }

    @Override
    protected void doStart() throws Exception {
        this.tempDataDB.start();
    }

    @Override
    protected void doClose() throws Exception {
        this.tempDataDB.close();
    }

    @Override
    protected void doStop() throws Exception {
        this.tempDataDB.stop();
    }

    @Override
    public CheckerConfig config() {
        return checkerConfig;
    }
}
