package com.carryxyh.check.redis;

import com.carryxyh.check.AbstractCheckStrategy;
import com.carryxyh.client.redis.RedisCacheClient;
import com.carryxyh.common.Command;
import com.carryxyh.common.DefaultCommand;
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

    private final CheckStrategys checkStrategys;

    public DefaultRedisCheckStrategy(RedisCacheClient source, RedisCacheClient target, CheckStrategys checkStrategys) {
        super(CheckStrategys.VALUE_EQUALS);
        this.checkStrategys = checkStrategys;
        this.source = source;
        this.target = target;
    }

    @Override
    public RedisCheckResult check(String key, Result<?> sourceValue, Result<?> targetValue) {
        Command c = DefaultCommand.nonValueCmd(key);
        StringResult sourceType = source.type(c);
        StringResult targetType = target.type(c);


        return null;
    }
}
