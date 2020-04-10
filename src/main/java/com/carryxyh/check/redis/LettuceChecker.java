package com.carryxyh.check.redis;

import com.carryxyh.CheckResult;
import com.carryxyh.CheckStrategy;
import com.carryxyh.TempData;
import com.carryxyh.TempDataDB;
import com.carryxyh.check.AbstractChecker;
import com.carryxyh.client.redis.lettuce.LettuceClient;
import com.carryxyh.common.Command;
import com.carryxyh.common.DefaultCommand;
import com.carryxyh.common.StringResult;
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

    protected RedisCheckStrategy checkStrategy;

    public LettuceChecker(TempDataDB tempDataDB,
                          LettuceClient source,
                          LettuceClient target) {
        super(tempDataDB, source, target);
    }

    @Override
    protected List<TempData> doCheck(List<String> keys) {
        List<TempData> conflictData = Lists.newArrayList();
        for (String key : keys) {
            Command c = DefaultCommand.nonValueCmd(key);
            StringResult sourceType = source.type(c);
            StringResult targetType = target.type(c);
            CheckResult check = checkStrategy.check(key, sourceType, targetType);
            if (check.isConflict()) {
                conflictData.add(toTempData(check, key, sourceType, targetType));
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
            this.checkStrategy = RedisKeyCheckStrategy.getInstance();
        } else if (checkStrategys == CheckStrategys.VALUE_EQUALS) {
            this.checkStrategy = new RedisValueCheckStrategy(source(), target());
        } else {
            throw new IllegalArgumentException("illegal check strategy");
        }
    }
}
