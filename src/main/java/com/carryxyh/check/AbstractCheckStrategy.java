package com.carryxyh.check;

import com.carryxyh.CheckResult;
import com.carryxyh.CheckStrategy;
import com.carryxyh.DefaultCheckResult;
import com.carryxyh.common.Result;
import com.carryxyh.constants.CheckStrategys;
import com.carryxyh.constants.ConflictType;

/**
 * AbstractCheckStrategy
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-09
 */
public abstract class AbstractCheckStrategy implements CheckStrategy {

    protected <T extends Result<?>> T checkAndCast(Result<?> result, Class<T> clazz) {
        return clazz.cast(result);
    }

    @Override
    public CheckResult check(String key, CheckStrategys checkStrategys) {
        if (sourceValue == null && targetValue == null) {
            return DefaultCheckResult.nonConflict();
        } else if (sourceValue == null) {
            return DefaultCheckResult.conflict(ConflictType.LACK_SOURCE, checkStrategys);
        } else if (targetValue == null) {
            return DefaultCheckResult.conflict(ConflictType.LACK_TARGET, checkStrategys);
        }
        return valueCheck(key, sourceValue, targetValue);
    }

    protected CheckResult valueCheck(String key, Result<?> sourceValue, Result<?> targetValue) {
        return DefaultCheckResult.nonConflict();
    }
}
