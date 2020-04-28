package com.carryxyh.check.redis;

import com.carryxyh.KeysInput;
import com.carryxyh.TempDataDB;
import com.carryxyh.check.AbstractChecker;
import com.carryxyh.client.redis.RedisCacheClient;
import com.carryxyh.input.IteratorKeysInput;
import com.carryxyh.tempdata.ConflictResultData;
import javafx.util.Pair;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * AbstractRedisChecker
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-28
 */
public abstract class AbstractRedisChecker<C extends RedisCacheClient> extends AbstractChecker<C> {

    protected AbstractRedisChecker(TempDataDB tempDataDB, C source, C target) {
        super(tempDataDB, source, target);
    }

    @Override
    protected void firstCheck(KeysInput input) {
        if (input instanceof IteratorKeysInput) {
            // iterator to get all keys.
            List<String> keys = input.input();
            while (CollectionUtils.isNotEmpty(keys)) {
                Map<Integer, List<Pair<String, String>>> hashed = hash(keys);
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

                await(countDownLatch);
                keys = input.input();
            }

            sleep();
        } else {
            super.firstCheck(input);
        }
    }
}
