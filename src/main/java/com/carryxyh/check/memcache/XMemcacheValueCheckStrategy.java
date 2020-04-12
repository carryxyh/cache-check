package com.carryxyh.check.memcache;

import com.carryxyh.CheckResult;
import com.carryxyh.DefaultCheckResult;
import com.carryxyh.check.AbstractCheckStrategy;
import com.carryxyh.client.memcache.xmemcache.XMemcacheClient;
import com.carryxyh.client.memcache.xmemcache.XMemcachedResult;
import com.carryxyh.common.Command;
import com.carryxyh.common.DefaultCommand;
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
final class XMemcacheValueCheckStrategy extends AbstractCheckStrategy<XMemcacheClient, XMemcacheClient> {

    XMemcacheValueCheckStrategy(XMemcacheClient source,
                                XMemcacheClient target) {
        super(source, target);
    }

    @Override
    public CheckResult check(String key) {
        CheckResult keyCheck = keyCheck(key);
        if (keyCheck.isConflict()) {
            return keyCheck;
        }

        Command getCmd = DefaultCommand.nonValueCmd(key);
        XMemcachedResult sourceValue = source.get(getCmd);
        XMemcachedResult targetValue = target.get(getCmd);

        XMemcachedResult source = checkAndCast(sourceValue, XMemcachedResult.class);
        XMemcachedResult target = checkAndCast(targetValue, XMemcachedResult.class);

        byte[] sv = source.result();
        byte[] tv = target.result();

        boolean equals = Arrays.equals(sv, tv);
        if (equals) {
            return DefaultCheckResult.nonConflict();
        }
        return DefaultCheckResult.conflict(ConflictType.VALUE, CheckStrategys.VALUE_EQUALS, sv, tv);
    }
}
