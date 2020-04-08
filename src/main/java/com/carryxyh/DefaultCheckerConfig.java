package com.carryxyh;

import com.carryxyh.config.AbstractConfig;
import com.carryxyh.constants.CheckStrategys;

/**
 * DefaultCheckerConfig
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public class DefaultCheckerConfig extends AbstractConfig implements CheckerConfig {

    @Override
    public TempDataDB buildTempDataDB() {
        return null;
    }

    @Override
    public CacheClient buildSource() {
        return null;
    }

    @Override
    public CacheClient buildTarget() {
        return null;
    }

    @Override
    public CheckStrategys getCheckStrategy() {
        return null;
    }

    @Override
    public int getRounds() {
        return 0;
    }

    @Override
    public long getInternal() {
        return 0;
    }

    @Override
    public int getComplexStructureThreshold() {
        return 0;
    }

    @Override
    public int getParallel() {
        return 0;
    }
}
