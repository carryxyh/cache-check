package com.carryxyh.check.memcache;

import com.carryxyh.CheckResult;
import com.carryxyh.DefaultCheckResult;
import com.carryxyh.check.AbstractCheckStrategy;
import com.carryxyh.client.memcache.xmemcache.XMemcacheClient;
import com.carryxyh.constants.ConflictType;
import com.carryxyh.constants.ValueType;

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
        CheckResult keyCheck = keyCheck(key, ValueType.MEMCACHE);
        if (keyCheck.isConflict()) {
            return keyCheck;
        }

        byte[] sourceValue = source.get(key);
        byte[] targetValue = target.get(key);

        boolean equals = Arrays.equals(sourceValue, targetValue);
        if (equals) {
            return DefaultCheckResult.nonConflict();
        }
        // we will not print value since it is a byte array, and it's content is meaningless.
        return DefaultCheckResult.conflict(ConflictType.VALUE, ValueType.MEMCACHE, null, null);
    }
}
