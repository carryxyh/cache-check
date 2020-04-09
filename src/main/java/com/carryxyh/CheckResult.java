package com.carryxyh;

import com.carryxyh.constants.CheckStrategys;
import com.carryxyh.constants.ConflictType;

/**
 * CheckResult
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public final class CheckResult {

    private boolean conflict;

    private ConflictType conflictType;

    private CheckStrategys checkStrategys;

    private CheckResult(CheckStrategys checkStrategys) {
        this.conflict = false;
        this.conflictType = null;
        this.checkStrategys = checkStrategys;
    }

    private CheckResult(ConflictType conflictType, CheckStrategys checkStrategys) {
        this.conflict = true;
        this.conflictType = conflictType;
        this.checkStrategys = checkStrategys;
    }

    public static CheckResult nonConflict(CheckStrategys checkStrategys) {
        return new CheckResult(checkStrategys);
    }

    public static CheckResult conflict(ConflictType conflictType, CheckStrategys checkStrategys) {
        return new CheckResult(conflictType, checkStrategys);
    }

    public boolean isConflict() {
        return conflict;
    }

    public ConflictType getConflictType() {
        return conflictType;
    }

    public CheckStrategys getCheckStrategys() {
        return checkStrategys;
    }
}
