package com.carryxyh.check.redis;

import com.carryxyh.KeysInput;
import com.carryxyh.TempDataDB;
import com.carryxyh.check.AbstractChecker;
import com.carryxyh.client.redis.RedisCacheClient;
import com.carryxyh.input.IteratorKeysInput;
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
                    executor.execute(new FirstCheckRunnable(hashed.get(x), countDownLatch, generateKey(0, x)));
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
