package com.carryxyh.check.redis;

import com.carryxyh.CheckResult;
import com.carryxyh.check.AbstractCheckStrategy;
import com.carryxyh.common.Result;
import com.carryxyh.constants.CheckStrategys;

/**
 * RedisKeyCheckStrategy
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-10
 */
public class RedisKeyCheckStrategy extends AbstractCheckStrategy implements RedisCheckStrategy {

    private static final RedisKeyCheckStrategy INSTANCE = new RedisKeyCheckStrategy();

    public static RedisKeyCheckStrategy getInstance() {
        return INSTANCE;
    }

    protected RedisKeyCheckStrategy() {
        super(CheckStrategys.KEY_EXISTS);
    }

    @Override
    public RedisCheckResult check(String key, Result<?> sourceValue, Result<?> targetValue) {
        CheckResult check = super.check(key, sourceValue, targetValue);
        return new RedisCheckResult(check);
    }
}
