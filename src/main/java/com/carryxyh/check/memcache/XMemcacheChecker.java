package com.carryxyh.check.memcache;

import com.carryxyh.CheckResult;
import com.carryxyh.CheckStrategy;
import com.carryxyh.TempData;
import com.carryxyh.TempDataDB;
import com.carryxyh.check.AbstractChecker;
import com.carryxyh.check.DefaultKeyCheckStrategy;
import com.carryxyh.client.memcache.xmemcache.XMemcacheClient;
import com.carryxyh.client.memcache.xmemcache.XMemcachedResult;
import com.carryxyh.common.Command;
import com.carryxyh.common.DefaultCommand;
import com.carryxyh.config.CheckerConfig;
import com.carryxyh.config.Config;
import com.carryxyh.constants.CheckStrategys;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * XMemcacheChecker
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public final class XMemcacheChecker extends AbstractChecker<XMemcacheClient, XMemcacheClient> {

    private CheckStrategy checkStrategy;

    public XMemcacheChecker(TempDataDB tempDataDB,
                            XMemcacheClient source,
                            XMemcacheClient target) {

        super(tempDataDB, source, target);
    }

    @Override
    protected List<TempData> doCheck(List<String> keys) {
        List<TempData> conflictData = Lists.newArrayList();
        for (String key : keys) {
            Command getCmd = DefaultCommand.nonValueCmd(key);
            XMemcachedResult sourceValue = source.get(getCmd);
            XMemcachedResult targetValue = target.get(getCmd);
            CheckResult check = checkStrategy.check(key, sourceValue, targetValue);
            if (check.isConflict()) {
                conflictData.add(toTempData(check, key, sourceValue, targetValue));
            }
        }
        return conflictData;
    }

    @Override
    protected void doInit(Config config) throws Exception {
        super.doInit(config);
        CheckerConfig checkerConfig = (CheckerConfig) config;
        CheckStrategys checkStrategys = checkerConfig.getCheckStrategys();
        if (checkStrategys == CheckStrategys.KEY_EXISTS) {
            this.checkStrategy = DefaultKeyCheckStrategy.getInstance();
        } else if (checkStrategys == CheckStrategys.VALUE_EQUALS) {
            this.checkStrategy = new XMemcacheValueCheckStrategy();
        } else {
            throw new IllegalArgumentException("illegal check strategy");
        }
    }
}
