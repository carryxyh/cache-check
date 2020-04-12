package com.carryxyh;

import com.carryxyh.constants.CheckStrategys;
import com.carryxyh.constants.ConflictType;

/**
 * DefaultCheckResult
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public class DefaultCheckResult implements CheckResult {

    private boolean conflict = false;

    private ConflictType conflictType;

    private CheckStrategys checkStrategys;

    private Object sourceValue;

    private Object targetValue;

    protected DefaultCheckResult() {
    }

    protected DefaultCheckResult(boolean conflict,
                                 ConflictType conflictType,
                                 CheckStrategys checkStrategys, Object sourceValue, Object targetValue) {
        this.conflict = conflict;
        this.conflictType = conflictType;
        this.checkStrategys = checkStrategys;
        this.sourceValue = sourceValue;
        this.targetValue = targetValue;
    }

    public static DefaultCheckResult nonConflict() {
        return new DefaultCheckResult();
    }

    public static DefaultCheckResult conflict(ConflictType conflictType,
                                              CheckStrategys checkStrategys,
                                              Object sourceValue,
                                              Object targetValue) {
        return new DefaultCheckResult(true, conflictType, checkStrategys, sourceValue, targetValue);
    }

    @Override
    public boolean isConflict() {
        return conflict;
    }

    @Override
    public ConflictType getConflictType() {
        return conflictType;
    }

    @Override
    public CheckStrategys getCheckStrategys() {
        return checkStrategys;
    }

    @Override
    public Object sourceValue() {
        return sourceValue;
    }

    @Override
    public Object targetValue() {
        return targetValue;
    }
}
