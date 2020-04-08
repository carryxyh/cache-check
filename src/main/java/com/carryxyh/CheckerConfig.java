package com.carryxyh;

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

    CheckStrategy getCheckStrategy();

    int getRounds();

    long getInternal();

    int getComplexStructureThreshold();

    int getParallel();
}
