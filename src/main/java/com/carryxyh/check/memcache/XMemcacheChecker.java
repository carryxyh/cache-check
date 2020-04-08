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

    protected XMemcacheChecker(CheckerConfig checkerConfig) {
        super(checkerConfig);
    }

    @Override
    protected void doCheck(List<String> keys, int round, CountDownLatch countDownLatch, CheckStrategy checkStrategy) {
        XMemcacheClient realSource = (XMemcacheClient) source;
        XMemcacheClient realTarget = (XMemcacheClient) target;
        for (String key : keys) {
            Command getCmd = new DefaultCommand(key, null);
            XMemcachedResult sourceValue = realSource.get(getCmd);
            XMemcachedResult targetValue = realTarget.get(getCmd);
        }
        countDownLatch.countDown();
    }
}
