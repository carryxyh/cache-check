package com.carryxyh.check;

import com.carryxyh.constants.CheckStrategys;

/**
 * DefaultKeyCheckStrategy
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-09
 */
public class DefaultKeyCheckStrategy extends AbstractCheckStrategy {

    private static final DefaultKeyCheckStrategy INSTANCE = new DefaultKeyCheckStrategy();

    public static DefaultKeyCheckStrategy getInstance() {
        return INSTANCE;
    }

    protected DefaultKeyCheckStrategy() {
        super(CheckStrategys.KEY_EXISTS);
    }
}
