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
import com.carryxyh.constants.ValueType;
import com.carryxyh.tempdata.ConflictResultData;
import com.google.common.collect.Lists;
import javafx.util.Pair;

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
    protected List<ConflictResultData> doCheck(List<Pair<String, String>> keys) {
        List<ConflictResultData> conflictData = Lists.newArrayList();
        for (Pair<String, String> key : keys) {
            CheckResult check = checkStrategy.check(key.getKey(), key.getValue());
            if (check.isConflict()) {
                conflictData.add(toResult(check, key.getKey()));
            }
        }
        return conflictData;
    }

    @Override
    protected void doInit(Config config) {
        super.doInit(config);
        CheckerConfig checkerConfig = (CheckerConfig) config;
        CheckStrategys checkStrategys = checkerConfig.getCheckStrategys();
        if (checkStrategys == CheckStrategys.KEY_EXISTS) {
            this.checkStrategy = new AbstractCheckStrategy<XMemcacheClient>(source(), target()) {
                @Override
                public CheckResult check(String key, String subKey) {
                    return keyCheck(key, ValueType.MEMCACHE);
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
