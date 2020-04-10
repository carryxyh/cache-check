package com.carryxyh.check.redis;

import com.carryxyh.CheckResult;
import com.carryxyh.DefaultCheckResult;
import com.carryxyh.constants.CheckStrategys;
import com.carryxyh.constants.ConflictType;

/**
 * RedisCheckResult
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-10
 */
public class RedisCheckResult extends DefaultCheckResult {

    public RedisCheckResult(CheckResult result) {
        super(result.isConflict(), result.getConflictType(), result.getCheckStrategys());
    }

    public RedisCheckResult(CheckStrategys checkStrategys) {
        super(checkStrategys);
    }

    public RedisCheckResult(ConflictType conflictType, CheckStrategys checkStrategys) {
        super(conflictType, checkStrategys);
    }


}
