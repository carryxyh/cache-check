package com.carryxyh.check.redis;

import com.carryxyh.TempDataDB;
import com.carryxyh.check.AbstractChecker;
import com.carryxyh.client.redis.lettuce.LettuceClient;
import com.carryxyh.config.CheckerConfig;
import com.carryxyh.config.Config;
import com.carryxyh.constants.CheckStrategys;
import com.carryxyh.tempdata.TempData;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * LettuceChecker
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-09
 */
public final class LettuceChecker extends AbstractChecker<LettuceClient<?, ?, ?>> {

    public LettuceChecker(TempDataDB tempDataDB,
                          LettuceClient<?, ?, ?> source,
                          LettuceClient<?, ?, ?> target) {
        super(tempDataDB, source, target);
    }

    @Override
    protected List<TempData> doCheck(List<String> keys) {
        List<TempData> conflictData = Lists.newArrayList();
        for (String key : keys) {

        }
        return conflictData;
    }

    @Override
    protected void doInit(Config config) throws Exception {
        super.doInit(config);
        CheckerConfig checkerConfig = (CheckerConfig) config;
        CheckStrategys checkStrategys = checkerConfig.getCheckStrategys();
    }
}
