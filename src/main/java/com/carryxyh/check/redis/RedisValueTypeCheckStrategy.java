package com.carryxyh.check.redis;

import com.carryxyh.CheckResult;
import com.carryxyh.DefaultCheckResult;
import com.carryxyh.client.redis.RedisCacheClient;
import com.carryxyh.common.Command;
import com.carryxyh.common.DefaultCommand;
import com.carryxyh.common.StringResult;
import com.carryxyh.constants.CheckStrategys;
import com.carryxyh.constants.ConflictType;

/**
 * RedisValueTypeCheckStrategy
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-12
 */
class RedisValueTypeCheckStrategy extends RedisKeyExistCheckStrategy {

    public RedisValueTypeCheckStrategy(RedisCacheClient source, RedisCacheClient target) {
        super(source, target);
    }

    @Override
    public CheckResult check(String key) {
        CheckResult c = super.check(key);
        if (c.isConflict()) {
            return c;
        }

        Command typeCmd = DefaultCommand.nonValueCmd(key);
        StringResult sourceType = source.type(typeCmd);
        StringResult targetType = target.type(typeCmd);
        String st = sourceType.result();
        String tt = targetType.result();

        if (st.equals(tt)) {
            return DefaultCheckResult.nonConflict();
        } else {
            return DefaultCheckResult.conflict(ConflictType.VALUE_TYPE, CheckStrategys.VALUE_TYPE, st, tt);
        }
    }
}
