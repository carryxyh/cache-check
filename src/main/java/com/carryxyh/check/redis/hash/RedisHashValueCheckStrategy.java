package com.carryxyh.check.redis.hash;

import com.carryxyh.CheckResult;
import com.carryxyh.check.AbstractCheckStrategy;
import com.carryxyh.client.redis.RedisCacheClient;

/**
 * RedisHashValueCheckStrategy
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-12
 */
public class RedisHashValueCheckStrategy extends AbstractCheckStrategy<RedisCacheClient> {

    public RedisHashValueCheckStrategy(RedisCacheClient source, RedisCacheClient target) {
        super(source, target);
    }

    @Override
    public CheckResult check(String key) {
        return null;
    }
}
