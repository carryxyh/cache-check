package com.carryxyh.check.redis.hash;

import com.carryxyh.check.redis.AbstractRedisComplicitStructureCheckStrategy;
import com.carryxyh.check.redis.RedisComplicitCheckResult;
import com.carryxyh.client.redis.RedisCacheClient;

/**
 * RedisHashValueCheckStrategy
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-12
 */
public class RedisHashValueCheckStrategy extends AbstractRedisComplicitStructureCheckStrategy {

    public RedisHashValueCheckStrategy(RedisCacheClient source, RedisCacheClient target,
                                       int threshold, int batchCompareSize) {
        super(source, target, threshold, batchCompareSize);
    }

    @Override
    protected RedisComplicitCheckResult checkHoleKey(String key) {
        return null;
    }

    @Override
    protected RedisComplicitCheckResult checkMemberOrField(String key, String member) {
        return null;
    }
}
