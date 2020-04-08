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
