package com.carryxyh.check.redis.list;

import com.carryxyh.CheckResult;
import com.carryxyh.check.redis.AbstractRedisComplicitStructureCheckStrategy;
import com.carryxyh.check.redis.RedisComplicitCheckResult;
import com.carryxyh.client.redis.RedisCacheClient;

/**
 * RedisListValueCheckStrategy
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-12
 */
public class RedisListValueCheckStrategy extends AbstractRedisComplicitStructureCheckStrategy {

    public RedisListValueCheckStrategy(RedisCacheClient source, RedisCacheClient target,
                                       int threshold, int batchCompareSize) {
        super(source, target, threshold, batchCompareSize);
    }

    @Override
    public RedisComplicitCheckResult check(String key, String subKey) {
        return null;
    }
}
