package com.carryxyh.config;

import com.carryxyh.constants.CheckStrategys;

/**
 * CheckerConfig
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public class CheckerConfig implements Config {

    private CheckStrategys checkStrategys = CheckStrategys.VALUE_EQUALS;

    private int parallel = 10;

    private int rounds = 3;

    private long internal = 60L * 1000L;

    private int complicateThreshold = 512;

    public CheckStrategys getCheckStrategys() {
        return checkStrategys;
    }

    public void setCheckStrategys(CheckStrategys checkStrategys) {
        this.checkStrategys = checkStrategys;
    }

    public int getParallel() {
        return parallel;
    }

    public void setParallel(int parallel) {
        this.parallel = parallel;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public long getInternal() {
        return internal;
    }

    public void setInternal(long internal) {
        this.internal = internal;
    }
}
