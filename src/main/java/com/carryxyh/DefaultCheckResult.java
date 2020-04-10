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

    private boolean conflict;

    private ConflictType conflictType;

    private CheckStrategys checkStrategys;

    protected DefaultCheckResult(CheckStrategys checkStrategys) {
        this(false, null, checkStrategys);
    }

    protected DefaultCheckResult(ConflictType conflictType, CheckStrategys checkStrategys) {
        this(true, conflictType, checkStrategys);
    }

    protected DefaultCheckResult(boolean conflict, ConflictType conflictType, CheckStrategys checkStrategys) {
        this.conflict = conflict;
        this.conflictType = conflictType;
        this.checkStrategys = checkStrategys;
    }

    public static DefaultCheckResult nonConflict(CheckStrategys checkStrategys) {
        return new DefaultCheckResult(checkStrategys);
    }

    public static DefaultCheckResult conflict(ConflictType conflictType, CheckStrategys checkStrategys) {
        return new DefaultCheckResult(conflictType, checkStrategys);
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
}
