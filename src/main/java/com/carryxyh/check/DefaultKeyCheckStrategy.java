package com.carryxyh.check;

import com.carryxyh.check.AbstractCheckStrategy;
import com.carryxyh.constants.CheckStrategys;

/**
 * DefaultKeyCheckStrategy
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-09
 */
public class DefaultKeyCheckStrategy extends AbstractCheckStrategy {

    protected DefaultKeyCheckStrategy() {
        super(CheckStrategys.KEY_EXISTS);
    }
}
