package com.carryxyh.check.redis;

import com.carryxyh.CheckResult;
import com.carryxyh.client.redis.RedisCacheClient;

/**
 * RedisTypeValueCheckStrategy
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-12
 */
class RedisTypeValueCheckStrategy extends RedisValueTypeCheckStrategy {

    private ValueCheckStrategyFactory valueCheckStrategyFactory;

    public RedisTypeValueCheckStrategy(RedisCacheClient source, RedisCacheClient target) {
        super(source, target);
    }

    @Override
    public CheckResult check(String key, String subKey) {
        CheckResult check = super.check(key, subKey);
        if (check.isConflict()) {
            return check;
        }

        // type must be same when run here.
        return null;
    }
}
