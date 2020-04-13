package com.carryxyh.check.memcache;

import com.carryxyh.CheckResult;
import com.carryxyh.CheckStrategy;
import com.carryxyh.TempDataDB;
import com.carryxyh.check.AbstractCheckStrategy;
import com.carryxyh.check.AbstractChecker;
import com.carryxyh.client.memcache.xmemcache.XMemcacheClient;
import com.carryxyh.config.CheckerConfig;
import com.carryxyh.config.Config;
import com.carryxyh.constants.CheckStrategys;
import com.carryxyh.tempdata.TempData;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * XMemcacheChecker
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public final class XMemcacheChecker extends AbstractChecker<XMemcacheClient> {

    private CheckStrategy<XMemcacheClient> checkStrategy;

    public XMemcacheChecker(TempDataDB tempDataDB,
                            XMemcacheClient source,
                            XMemcacheClient target) {

        super(tempDataDB, source, target);
    }

    @Override
    protected List<TempData> doCheck(List<String> keys) {
        List<TempData> conflictData = Lists.newArrayList();
        for (String key : keys) {

            CheckResult check = checkStrategy.check(key);
            if (check.isConflict()) {
                conflictData.add(toTempData(check, key, check.sourceValue(), check.targetValue()));
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
            this.checkStrategy = new AbstractCheckStrategy<XMemcacheClient>(source(), target()) {
                @Override
                public CheckResult check(String key) {
                    return keyCheck(key);
                }
            };
        } else if (checkStrategys == CheckStrategys.VALUE_EQUALS) {
            this.checkStrategy = new XMemcacheValueCheckStrategy(source(), target());
        } else if (checkStrategys == CheckStrategys.VALUE_TYPE) {
            throw new IllegalArgumentException(
                    "illegal check strategy for xmemcache client since all value should be byte[].");
        } else {
            throw new IllegalArgumentException("can't match check strategy for : " + checkStrategys.name());
        }
    }
}
