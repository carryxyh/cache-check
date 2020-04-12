package com.carryxyh.check.redis;

import com.carryxyh.CheckResult;

/**
 * ValueCheckStrategyFactory
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-12
 */
public interface ValueCheckStrategyFactory {

    CheckResult valueCheck(String key, String type);
}
