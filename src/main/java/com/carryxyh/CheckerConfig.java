package com.carryxyh;

import com.carryxyh.constants.CheckStrategys;

/**
 * CheckerConfig
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public interface CheckerConfig extends Config {

    TempDataDB buildTempDataDB();

    CacheClient buildSource();

    CacheClient buildTarget();

    CheckStrategys getCheckStrategy();

    int getRounds();

    long getInternal();

    int getComplexStructureThreshold();

    int getParallel();
}
