package com.carryxyh;

import com.carryxyh.constants.ConflictType;
import com.carryxyh.constants.ValueType;

/**
 * DefaultCheckResult
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public class DefaultCheckResult implements CheckResult {

    private static final long serialVersionUID = 1386645363263736325L;

    private boolean conflict = false;

    private ConflictType conflictType;

    private String subKey;

    private ValueType valueType;

    private Object sourceValue;

    private Object targetValue;

    protected DefaultCheckResult() {

    }

    protected DefaultCheckResult(boolean conflict,
                                 ConflictType conflictType,
                                 ValueType valueType,
                                 String subKey,
                                 Object sourceValue,
                                 Object targetValue) {

        this.conflict = conflict;
        this.valueType = valueType;
        this.conflictType = conflictType;
        this.sourceValue = sourceValue;
        this.targetValue = targetValue;
        this.subKey = subKey;
    }

    public static DefaultCheckResult nonConflict() {
        return new DefaultCheckResult();
    }

    public static DefaultCheckResult conflict(ConflictType conflictType,
                                              ValueType valueType,
                                              String subKey,
                                              Object sourceValue,
                                              Object targetValue) {
        return new DefaultCheckResult(true, conflictType, valueType, subKey, sourceValue, targetValue);
    }

    public static DefaultCheckResult conflict(ConflictType conflictType,
                                              ValueType valueType,
                                              Object sourceValue,
                                              Object targetValue) {
        return new DefaultCheckResult(true, conflictType, valueType, null, sourceValue, targetValue);
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
    public ValueType valueType() {
        return valueType;
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
