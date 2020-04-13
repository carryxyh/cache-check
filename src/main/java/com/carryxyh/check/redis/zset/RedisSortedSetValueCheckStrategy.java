package com.carryxyh.check.redis.zset;

import com.carryxyh.CheckResult;
import com.carryxyh.check.AbstractCheckStrategy;
import com.carryxyh.client.redis.RedisCacheClient;

/**
 * RedisSortedSetValueCheckStrategy
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-12
 */
public class RedisSortedSetValueCheckStrategy extends AbstractCheckStrategy<RedisCacheClient> {

    public RedisSortedSetValueCheckStrategy(RedisCacheClient source, RedisCacheClient target) {
        super(source, target);
    }

    @Override
    public CheckResult check(String key) {
        return null;
    }
}
