package com.carryxyh.check.redis;

import com.carryxyh.check.AbstractCheckStrategy;
import com.carryxyh.constants.CheckStrategys;

/**
 * RedisValueCheckStrategy
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-10
 */
public class RedisValueCheckStrategy extends AbstractCheckStrategy {

    protected RedisValueCheckStrategy() {
        super(CheckStrategys.VALUE_EQUALS);
    }


}
