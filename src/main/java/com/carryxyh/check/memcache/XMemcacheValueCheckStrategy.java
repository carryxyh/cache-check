package com.carryxyh.check.memcache;

import com.carryxyh.CheckResult;
import com.carryxyh.check.AbstractCheckStrategy;
import com.carryxyh.client.memcache.xmemcache.XMemcachedResult;
import com.carryxyh.common.Result;
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
public final class XMemcacheValueCheckStrategy extends AbstractCheckStrategy {

    protected XMemcacheValueCheckStrategy() {
        super(CheckStrategys.VALUE_EQUALS);
    }

    @Override
    protected CheckResult valueCheck(String key, Result sourceValue, Result targetValue) {
        XMemcachedResult source = checkAndCast(sourceValue, XMemcachedResult.class);
        XMemcachedResult target = checkAndCast(targetValue, XMemcachedResult.class);

        byte[] sv = source.result();
        byte[] tv = target.result();

        boolean equals = Arrays.equals(sv, tv);
        if (equals) {
            return CheckResult.nonConflict(checkStrategys);
        }
        return CheckResult.conflict(ConflictType.VALUE, checkStrategys);
    }
}
