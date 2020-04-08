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
public abstract class AbstractMemcacheChecker extends AbstractChecker {

    protected MemcacheCacheClient source;

    protected MemcacheCacheClient target;

    protected AbstractMemcacheChecker(CheckerConfig checkerConfig) {
        super(checkerConfig);
    }

    @Override
    protected void doInit() throws Exception {
        super.doInit();
        this.source = (MemcacheCacheClient) checkerConfig.buildSource();
        this.target = (MemcacheCacheClient) checkerConfig.buildTarget();
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
        this.source.start();
        this.target.start();
    }

    @Override
    protected void doStop() throws Exception {
        super.doStop();
        this.source.stop();
        this.target.stop();
    }

    @Override
    protected void doClose() throws Exception {
        super.doClose();
        this.source.close();
        this.target.close();
    }
}
