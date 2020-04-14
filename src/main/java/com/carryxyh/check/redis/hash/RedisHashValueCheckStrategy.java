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
    protected CheckResult checkHoleKey(String key) {
        return null;
    }

    @Override
    protected CheckResult checkMemberOrField(String key, String member) {
        return null;
    }
}
