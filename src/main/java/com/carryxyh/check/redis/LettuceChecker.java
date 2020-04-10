package com.carryxyh.check.redis;

import com.carryxyh.TempData;
import com.carryxyh.TempDataDB;
import com.carryxyh.check.AbstractChecker;
import com.carryxyh.check.DefaultKeyCheckStrategy;
import com.carryxyh.client.redis.lettuce.LettuceClient;
import com.carryxyh.config.CheckerConfig;
import com.carryxyh.config.Config;
import com.carryxyh.constants.CheckStrategys;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * LettuceChecker
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-09
 */
public final class LettuceChecker extends AbstractChecker<LettuceClient, LettuceClient> {

    public LettuceChecker(TempDataDB tempDataDB,
                          LettuceClient source,
                          LettuceClient target) {
        super(tempDataDB, source, target);
    }

    @Override
    protected List<TempData> doCheck(List<String> keys) {
        List<TempData> result = Lists.newArrayList();
        for (String s : keys) {

        }
        return result;
    }

    @Override
    protected void doInit(Config config) throws Exception {
        super.doInit(config);
        CheckerConfig checkerConfig = (CheckerConfig) config;
        CheckStrategys checkStrategys = checkerConfig.getCheckStrategys();
        if (checkStrategys == CheckStrategys.KEY_EXISTS) {
            this.checkStrategy = DefaultKeyCheckStrategy.getInstance();
        } else {

        }
    }
}
