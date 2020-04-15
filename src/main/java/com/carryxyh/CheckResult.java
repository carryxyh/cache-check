package com.carryxyh;

import com.carryxyh.constants.ConflictType;
import com.carryxyh.constants.ValueType;

/**
 * CheckResult
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-10
 */
public interface CheckResult {

    boolean isConflict();

    ConflictType getConflictType();

    ValueType valueType();

    String subKey();

    Object sourceValue();

    Object targetValue();
}
