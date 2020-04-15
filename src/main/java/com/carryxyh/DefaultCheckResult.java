package com.carryxyh;

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

    private String subKey;

    private Object sourceValue;

    private Object targetValue;

    protected DefaultCheckResult() {
    }

    protected DefaultCheckResult(boolean conflict,
                                 ConflictType conflictType, String subKey, Object sourceValue, Object targetValue) {
        this.conflict = conflict;
        this.conflictType = conflictType;
        this.sourceValue = sourceValue;
        this.targetValue = targetValue;
        this.subKey = subKey;
    }

    public static DefaultCheckResult nonConflict() {
        return new DefaultCheckResult();
    }

    public static DefaultCheckResult conflict(ConflictType conflictType,
                                              String subKey,
                                              Object sourceValue,
                                              Object targetValue) {
        return new DefaultCheckResult(true, conflictType, subKey, sourceValue, targetValue);
    }

    public static DefaultCheckResult conflict(ConflictType conflictType,
                                              Object sourceValue,
                                              Object targetValue) {
        return new DefaultCheckResult(true, conflictType, null, sourceValue, targetValue);
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
    public String subKey() {
        return subKey;
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
