package com.carryxyh.check.memcache;

import com.carryxyh.CheckerConfig;
import com.carryxyh.check.AbstractChecker;
import com.carryxyh.client.memcache.MemcacheCacheClient;

/**
 * AbstractMemcacheChecker
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public abstract class AbstractMemcacheChecker<SOURCE extends MemcacheCacheClient, TARGET extends MemcacheCacheClient>
        extends AbstractChecker<SOURCE, TARGET> {

    protected AbstractMemcacheChecker(CheckerConfig checkerConfig) {
        super(checkerConfig);
    }

}
