package com.carryxyh.check.redis.list;

import com.carryxyh.CheckResult;
import com.carryxyh.check.AbstractCheckStrategy;
import com.carryxyh.client.redis.RedisCacheClient;

/**
 * RedisListValueCheckStrategy
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-12
 */
public class RedisListValueCheckStrategy extends AbstractCheckStrategy<RedisCacheClient> {

    public RedisListValueCheckStrategy(RedisCacheClient source, RedisCacheClient target) {
        super(source, target);
    }

    @Override
    public CheckResult check(String key) {
        return null;
    }
}
