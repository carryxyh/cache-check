package com.carryxyh.check.memcache;

import com.carryxyh.CheckerConfig;
import com.carryxyh.check.AbstractChecker;

/**
 * AbstractMemcacheChecker
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public abstract class AbstractMemcacheChecker extends AbstractChecker {

    protected AbstractMemcacheChecker(CheckerConfig checkerConfig) {
        super(checkerConfig);
    }

}
