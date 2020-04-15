package com.carryxyh;

import com.carryxyh.constants.ConflictType;

/**
 * CheckResult
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-10
 */
public interface CheckResult {

    boolean isConflict();

    ConflictType getConflictType();

    String subKey();

    Object sourceValue();

    Object targetValue();
}
