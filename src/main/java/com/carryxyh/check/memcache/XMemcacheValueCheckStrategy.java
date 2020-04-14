package com.carryxyh.check.memcache;

import com.carryxyh.CheckResult;
import com.carryxyh.DefaultCheckResult;
import com.carryxyh.check.AbstractCheckStrategy;
import com.carryxyh.client.memcache.xmemcache.XMemcacheClient;
import com.carryxyh.constants.CheckStrategys;
import com.carryxyh.constants.ConflictType;

import java.util.Arrays;

/**
 * XMemcacheValueCheckStrategy
 * xmemcache client use byte to compare
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-09
 */
final class XMemcacheValueCheckStrategy extends AbstractCheckStrategy<XMemcacheClient> {

    XMemcacheValueCheckStrategy(XMemcacheClient source,
                                XMemcacheClient target) {
        super(source, target);
    }

    @Override
    public CheckResult check(String key, String subKey) {
        CheckResult keyCheck = keyCheck(key);
        if (keyCheck.isConflict()) {
            return keyCheck;
        }

        byte[] sourceValue = source.get(key);
        byte[] targetValue = target.get(key);

        boolean equals = Arrays.equals(sourceValue, targetValue);
        if (equals) {
            return DefaultCheckResult.nonConflict();
        }
        return DefaultCheckResult.conflict(ConflictType.VALUE, CheckStrategys.VALUE_EQUALS, sourceValue, targetValue);
    }
}
