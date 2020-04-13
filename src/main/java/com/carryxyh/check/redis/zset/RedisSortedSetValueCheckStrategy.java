package com.carryxyh.check.redis.zset;

import com.carryxyh.CheckResult;
import com.carryxyh.DefaultCheckResult;
import com.carryxyh.check.redis.AbstractConflictThresholdCheckStrategy;
import com.carryxyh.client.redis.RedisCacheClient;

/**
 * RedisSortedSetValueCheckStrategy
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-12
 */
public class RedisSortedSetValueCheckStrategy extends AbstractConflictThresholdCheckStrategy {

    public RedisSortedSetValueCheckStrategy(RedisCacheClient source, RedisCacheClient target,
                                            int threshold, int batchCompareSize) {
        super(source, target, threshold, batchCompareSize);
    }

    @Override
    public CheckResult check(String key) {
        Long size = source.zcard(key);
        if (size == null) {
            // key not exists.
            return DefaultCheckResult.nonConflict();
        }

        if (size > threshold) {
            // scan to compare.

        } else {

        }
        return null;
    }
}
