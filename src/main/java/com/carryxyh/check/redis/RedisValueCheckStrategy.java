package com.carryxyh.check.redis;

import com.carryxyh.check.AbstractCheckStrategy;
import com.carryxyh.client.redis.RedisCacheClient;
import com.carryxyh.common.Result;
import com.carryxyh.common.StringResult;
import com.carryxyh.constants.CheckStrategys;

/**
 * RedisValueCheckStrategy
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-10
 */
public class RedisValueCheckStrategy extends AbstractCheckStrategy implements RedisCheckStrategy {

    private final RedisCacheClient source;

    private final RedisCacheClient target;

    public RedisValueCheckStrategy(RedisCacheClient source, RedisCacheClient target) {
        super(CheckStrategys.VALUE_EQUALS);
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
