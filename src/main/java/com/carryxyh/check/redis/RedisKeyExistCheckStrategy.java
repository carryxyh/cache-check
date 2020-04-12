package com.carryxyh.check.redis;

import com.carryxyh.CheckResult;
import com.carryxyh.DefaultCheckResult;
import com.carryxyh.check.AbstractCheckStrategy;
import com.carryxyh.client.redis.RedisCacheClient;
import com.carryxyh.common.Command;
import com.carryxyh.common.DefaultCommand;
import com.carryxyh.common.StringResult;
import com.carryxyh.constants.CheckStrategys;
import com.carryxyh.constants.ConflictType;

/**
 * RedisKeyExistCheckStrategy
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-12
 */
class RedisKeyExistCheckStrategy extends AbstractCheckStrategy<RedisCacheClient, RedisCacheClient> {

    protected static final String NONE = "none";

    public RedisKeyExistCheckStrategy(RedisCacheClient source, RedisCacheClient target) {
        super(source, target);
    }

    @Override
    public CheckResult check(String key) {
        Command typeCmd = DefaultCommand.nonValueCmd(key);
        StringResult sourceType = source.type(typeCmd);
        StringResult targetType = target.type(typeCmd);
        String st = sourceType.result();
        String tt = targetType.result();

        if (st.equals(tt)) {
            // 1. st == tt == none.
            // 2. st == tt, means same type.
            return DefaultCheckResult.nonConflict();
        } else {
            if (NONE.equals(st)) {
                // st == none, tt != none.
                return DefaultCheckResult.conflict(ConflictType.LACK_SOURCE, CheckStrategys.KEY_EXISTS, st, tt);
            } else if (NONE.equals(tt)) {
                // tt == none, st != none.
                return DefaultCheckResult.conflict(ConflictType.LACK_TARGET, CheckStrategys.KEY_EXISTS, st, tt);
            } else {
                // st != tt, and both of them != none.
                // means not the same type.
                return DefaultCheckResult.nonConflict();
            }
        }
    }
}
