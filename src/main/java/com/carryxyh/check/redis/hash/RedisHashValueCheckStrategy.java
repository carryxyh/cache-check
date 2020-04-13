package com.carryxyh.check.redis.hash;

import com.carryxyh.CheckResult;
import com.carryxyh.check.redis.AbstractConflictThresholdCheckStrategy;
import com.carryxyh.client.redis.RedisCacheClient;

/**
 * RedisHashValueCheckStrategy
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-12
 */
public class RedisHashValueCheckStrategy extends AbstractConflictThresholdCheckStrategy {

    public RedisHashValueCheckStrategy(RedisCacheClient source, RedisCacheClient target,
                                       int threshold, int batchCompareSize) {
        super(source, target, threshold, batchCompareSize);
    }

    @Override
    public CheckResult check(String key) {
        return null;
    }
}
