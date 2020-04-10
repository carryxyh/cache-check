package com.carryxyh;

import com.carryxyh.constants.CheckStrategys;

/**
 * CheckStrategy
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public interface CheckStrategy {

    CheckResult check(String key, CheckStrategys checkStrategys);
}
