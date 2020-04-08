package com.carryxyh.check.memcache;

import com.carryxyh.CheckStrategy;
import com.carryxyh.CheckerConfig;
import com.carryxyh.client.memcache.xmemcache.XMemcacheClient;
import com.carryxyh.client.memcache.xmemcache.XMemcachedResult;
import com.carryxyh.common.Command;
import com.carryxyh.common.DefaultCommand;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * XMemcacheChecker
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public final class XMemcacheChecker extends AbstractMemcacheChecker {

    protected XMemcacheClient source;

    protected XMemcacheClient target;

    private CheckStrategy checkStrategy;

    public XMemcacheChecker(CheckerConfig checkerConfig) {
        super(checkerConfig);
    }

    @Override
    protected void doCheck(List<String> keys, CountDownLatch countDownLatch) {
        int rounds = checkerConfig.getRounds();
        long internal = checkerConfig.getInternal();
        for (String key : keys) {
            Command getCmd = new DefaultCommand(key, null);
            XMemcachedResult sourceValue = source.get(getCmd);
            XMemcachedResult targetValue = target.get(getCmd);
        }
        countDownLatch.countDown();
    }

    @Override
    protected void doInit() throws Exception {
        super.doInit();
        this.source = (XMemcacheClient) checkerConfig.buildSource();
        this.target = (XMemcacheClient) checkerConfig.buildTarget();
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

    @Override
    public XMemcacheClient source() {
        return source;
    }

    @Override
    public XMemcacheClient target() {
        return target;
    }
}
