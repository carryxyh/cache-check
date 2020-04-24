package com.carryxyh;

import com.carryxyh.constants.ConflictType;
import com.carryxyh.constants.ValueType;

import java.io.Serializable;

/**
 * CheckResult
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-10
 */
public interface CheckResult extends Serializable {

    boolean isConflict();

    ConflictType getConflictType();

    ValueType valueType();

    String subKey();

    Object sourceValue();

    Object targetValue();
}
