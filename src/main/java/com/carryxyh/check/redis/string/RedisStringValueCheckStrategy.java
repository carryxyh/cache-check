package com.carryxyh.check.redis.string;

import com.carryxyh.CheckResult;
import com.carryxyh.DefaultCheckResult;
import com.carryxyh.check.AbstractCheckStrategy;
import com.carryxyh.client.redis.RedisCacheClient;
import com.carryxyh.common.Command;
import com.carryxyh.common.DefaultCommand;
import com.carryxyh.common.StringResult;
import com.carryxyh.constants.CheckStrategys;
import com.carryxyh.constants.ConflictType;
import org.apache.commons.lang3.StringUtils;

/**
 * RedisStringValueCheckStrategy
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-12
 */
class RedisStringValueCheckStrategy extends AbstractCheckStrategy<RedisCacheClient> {

    public RedisStringValueCheckStrategy(RedisCacheClient source, RedisCacheClient target) {
        super(source, target);
    }

    @Override
    public CheckResult check(String key) {
        Command getCmd = DefaultCommand.nonValueCmd(key);
        StringResult sourceValue = source.get(getCmd);
        StringResult targetValue = target.get(getCmd);
        String sv = sourceValue.result();
        String tv = targetValue.result();
        if (StringUtils.equals(sv, tv)) {
            return DefaultCheckResult.nonConflict();
        } else {
            return DefaultCheckResult.conflict(ConflictType.VALUE, CheckStrategys.VALUE_EQUALS, sv, tv);
        }
    }
}