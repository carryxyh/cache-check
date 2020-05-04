package com.carryxyh.check;

import com.carryxyh.CacheClient;
import com.carryxyh.CheckResult;
import com.carryxyh.Checker;
import com.carryxyh.KeysInput;
import com.carryxyh.TempDataDB;
import com.carryxyh.config.CheckerConfig;
import com.carryxyh.config.Config;
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

    // private field. -------------------------------------------------------------------------------------------------

    protected final Executor executor =
            new ThreadPerTaskExecutor(new NamedThreadFactory("checker-", true));

    protected int parallel;

    private int rounds;

    protected long internal;

    // protected field. -----------------------------------------------------------------------------------------------

    protected C source;

    protected C target;

    protected TempDataDB tempDataDB;

    protected AbstractChecker(TempDataDB tempDataDB, C source, C target) {
        this.tempDataDB = tempDataDB;
        this.source = source;
        this.target = target;
    }

    protected class FirstCheckRunnable implements Runnable {

        private final List<Pair<String, String>> needDiff;

        private final CountDownLatch countDownLatch;

        private final String key;

        public FirstCheckRunnable(List<Pair<String, String>> needDiff, CountDownLatch countDownLatch, String key) {
            this.needDiff = needDiff;
            this.countDownLatch = countDownLatch;
            this.key = key;
        }

        @Override
        public void run() {
            List<ConflictResultData> tempData = doCheck(needDiff);
            if (CollectionUtils.isNotEmpty(tempData)) {
                AbstractChecker.this.tempDataDB.save(key, tempData);
            }
            countDownLatch.countDown();
        }
    }

    protected class AroundCheckRunnable implements Runnable {

        private final CountDownLatch countDownLatch;

        private final String key;

        public AroundCheckRunnable(CountDownLatch countDownLatch, String key) {
            this.countDownLatch = countDownLatch;
            this.key = key;
        }

        @Override
        public void run() {
            List<ConflictResultData> load = AbstractChecker.this.tempDataDB.load(key);
            if (CollectionUtils.isNotEmpty(load)) {

                List<ConflictResultData> tempData = doCheck(load.
                        stream().
                        map(conflictResultData ->
                                new Pair<>(conflictResultData.getKey(),
                                        conflictResultData.getFieldOrSubKey())).
                        collect(Collectors.toList()));
                if (CollectionUtils.isNotEmpty(tempData)) {
                    AbstractChecker.this.tempDataDB.save(key, tempData);
                }
            }
            countDownLatch.countDown();
        }
    }

    @Override
    public List<ConflictResultData> check(KeysInput input) {

        firstCheck(input);

        aroundCheck();

        // last rounds result as final result.
        List<ConflictResultData> result = Lists.newArrayList();
        for (int i = 0; i < parallel; i++) {
            List<ConflictResultData> load = tempDataDB.load(generateKey(rounds - 1, parallel));
            result.addAll(load);
        }
        return result;
    }

    protected void aroundCheck() {
        // around check, check data in temp data db.
        for (int i = 1; i < rounds; i++) {
            final CountDownLatch c = new CountDownLatch(parallel);
            for (int x = 0; x < parallel; x++) {
                executor.execute(new AroundCheckRunnable(c, generateKey(i, x)));
            }

            await(c);

            sleep();
        }
    }

    protected Map<Integer, List<Pair<String, String>>> hash(List<String> keys) {
        // hash.
        // use the index of key as a hash rule to prevent data skew.
        final Map<Integer, List<Pair<String, String>>> hashed = Maps.newHashMap();
        for (int i = 0; i < keys.size(); i++) {
            int h = i % parallel;
            List<Pair<String, String>> strings = hashed.computeIfAbsent(h, integer -> Lists.newArrayList());
            strings.add(new Pair<>(keys.get(i), null));
        }
        return hashed;
    }

    protected void firstCheck(KeysInput input) {
        List<String> keys = input.input();
        if (CollectionUtils.isEmpty(keys)) {
            return;
        }

        Map<Integer, List<Pair<String, String>>> hashed = hash(keys);

        // first check, use input keys.
        final CountDownLatch countDownLatch = new CountDownLatch(parallel);
        for (int x = 0; x < parallel; x++) {
            executor.execute(
                    new FirstCheckRunnable(hashed.get(x), countDownLatch, generateKey(0, x)));
        }

        await(countDownLatch);

        sleep();
    }

    protected String generateKey(int round, int parallel) {
        return round + "-" + parallel;
    }

    protected abstract List<ConflictResultData> doCheck(List<Pair<String, String>> keys);

    protected static ConflictResultData toResult(CheckResult check,
                                                 String key) {
        ConflictResultData t = new ConflictResultData();
        t.setConflictType(check.getConflictType().getType());
        t.setKey(key);
        t.setFieldOrSubKey(check.subKey());
        t.setSourceValue(check.sourceValue());
        t.setTargetValue(check.targetValue());
        t.setValueType(check.valueType().getType());
        return t;
    }

    protected void sleep() {
        try {
            Thread.sleep(internal);
        } catch (InterruptedException ignored) {
        }
    }

    protected static void await(CountDownLatch countDownLatch) {
        try {
            countDownLatch.await();
        } catch (InterruptedException ignored) {
        }
    }

    @Override
    protected void doInit(Config config) {
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
    public C source() {
        return source;
    }

    @Override
    public C target() {
        return target;
    }
}
