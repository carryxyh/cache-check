package com.carryxyh.check.redis;

import com.carryxyh.CheckResult;
import com.carryxyh.DefaultCheckResult;
import com.carryxyh.check.AbstractCheckStrategy;
import com.carryxyh.client.redis.RedisCacheClient;
import com.carryxyh.constants.ConflictType;
import com.carryxyh.constants.ValueType;

/**
 * RedisKeyExistCheckStrategy
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-12
 */
class RedisKeyExistCheckStrategy extends AbstractCheckStrategy<RedisCacheClient> {

    public RedisKeyExistCheckStrategy(RedisCacheClient source, RedisCacheClient target) {
        super(source, target);
    }

    @Override
    public CheckResult check(String key, String subKey) {
        String sourceType = source.type(key);
        String targetType = target.type(key);

        if (sourceType.equals(targetType)) {
            // 1. st == tt == none.
            // 2. st == tt, means same type.
            return DefaultCheckResult.nonConflict();
        } else {
            if (ValueType.NONE.name().equals(sourceType)) {
                // st == none, tt != none.
                return DefaultCheckResult.
                        conflict(ConflictType.LACK_SOURCE, ValueType.NONE, sourceType, targetType);
            } else if (ValueType.NONE.name().equals(targetType)) {
                // tt == none, st != none.
                return DefaultCheckResult.
                        conflict(ConflictType.LACK_TARGET,
                                ValueType.nameOf(sourceType.toLowerCase()), sourceType, targetType);
            } else {
                // st != tt, and both of them != none.
                // means not the same type.
                return DefaultCheckResult.nonConflict();
            }
        }
    }
}
