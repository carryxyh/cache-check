package com.carryxyh.check;

import com.carryxyh.CheckStrategy;
import com.carryxyh.Checker;
import com.carryxyh.CheckerConfig;
import com.carryxyh.KeysInput;
import com.carryxyh.TempDataDB;
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
public abstract class AbstractChecker extends Endpoint implements Checker {

    protected final CheckerConfig checkerConfig;

    protected TempDataDB tempDataDB;

    private final Executor executor = new ThreadPerTaskExecutor(new NamedThreadFactory("checker-", true));

    protected AbstractChecker(CheckerConfig checkerConfig) {
        this.checkerConfig = checkerConfig;
    }

    @Override
    public void check(KeysInput input) {
        List<String> keys = input.input();
        if (CollectionUtils.isEmpty(keys)) {
            return;
        }

        int parallel = checkerConfig.getParallel();
        final Map<Integer, List<String>> hashed = Maps.newHashMap();
        for (String k : keys) {
            int i = k.hashCode();
            int h = i % parallel;
            List<String> strings = hashed.computeIfAbsent(h, integer -> Lists.newArrayList());
            strings.add(k);
        }

        final CountDownLatch countDownLatch = new CountDownLatch(parallel);
        for (int x = 0; x < parallel; x++) {
            final int temp = x;
            executor.execute(() -> {
                List<String> needDiff = hashed.get(temp);
                doCheck(needDiff, countDownLatch);
            });
        }
    }

    protected abstract void doCheck(List<String> keys, CountDownLatch countDownLatch);

    @Override
    protected void doInit() throws Exception {
        this.tempDataDB = checkerConfig.buildTempDataDB();
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
