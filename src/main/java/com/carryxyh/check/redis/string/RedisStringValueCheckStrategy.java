package com.carryxyh.check.redis.string;

import com.carryxyh.CheckResult;
import com.carryxyh.DefaultCheckResult;
import com.carryxyh.check.AbstractCheckStrategy;
import com.carryxyh.client.redis.RedisCacheClient;
import com.carryxyh.constants.ConflictType;
import com.carryxyh.constants.ValueType;
import org.apache.commons.lang3.StringUtils;

/**
 * RedisStringValueCheckStrategy
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-12
 */
public class RedisStringValueCheckStrategy extends AbstractCheckStrategy<RedisCacheClient> {

    public RedisStringValueCheckStrategy(RedisCacheClient source, RedisCacheClient target) {
        super(source, target);
    }

    @Override
    public CheckResult check(String key, String subKey) {
        String sourceValue = source.get(key);
        String targetValue = target.get(key);
        if (StringUtils.equals(sourceValue, targetValue)) {
            return DefaultCheckResult.nonConflict();
        } else {
            return DefaultCheckResult.
                    conflict(ConflictType.VALUE, ValueType.STRING, sourceValue, targetValue);
        }
    }
}
