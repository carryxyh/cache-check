package com.carryxyh.check.redis;

import com.carryxyh.CheckStrategy;
import com.carryxyh.common.Result;

/**
 * RedisCheckStrategy
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-10
 */
public interface RedisCheckStrategy extends CheckStrategy {

    @Override
    RedisCheckResult check(String key, Result<?> sourceValue, Result<?> targetValue);
}
