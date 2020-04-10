package com.carryxyh;

import com.carryxyh.common.Result;

/**
 * CheckStrategy
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public interface CheckStrategy {

    CheckResult check(String key, Result<?> sourceValue, Result<?> targetValue);
}
