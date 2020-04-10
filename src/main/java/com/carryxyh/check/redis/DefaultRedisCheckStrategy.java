package com.carryxyh.check.redis;

import com.carryxyh.check.AbstractCheckStrategy;
import com.carryxyh.client.redis.RedisCacheClient;
import com.carryxyh.common.Result;
import com.carryxyh.common.StringResult;
import com.carryxyh.constants.CheckStrategys;

/**
 * DefaultRedisCheckStrategy
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-10
 */
public class DefaultRedisCheckStrategy extends AbstractCheckStrategy implements RedisCheckStrategy {

    private final RedisCacheClient source;

    private final RedisCacheClient target;

    private final boolean valueCheck;

    public DefaultRedisCheckStrategy(RedisCacheClient source, RedisCacheClient target, boolean valueCheck) {
        super(CheckStrategys.VALUE_EQUALS);
        this.valueCheck = valueCheck;
        this.source = source;
        this.target = target;
    }

    @Override
    public RedisCheckResult check(String key, Result<?> sourceValue, Result<?> targetValue) {
        StringResult sourceType = checkAndCast(sourceValue, StringResult.class);
        StringResult targetType = checkAndCast(targetValue, StringResult.class);



        return null;
    }
}
