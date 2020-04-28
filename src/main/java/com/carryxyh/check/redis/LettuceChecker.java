package com.carryxyh.check.redis;

import com.carryxyh.CheckResult;
import com.carryxyh.CheckStrategy;
import com.carryxyh.DefaultCheckResult;
import com.carryxyh.TempDataDB;
import com.carryxyh.check.AbstractChecker;
import com.carryxyh.client.redis.RedisCacheClient;
import com.carryxyh.client.redis.lettuce.LettuceClient;
import com.carryxyh.config.CheckerConfig;
import com.carryxyh.config.Config;
import com.carryxyh.constants.CheckStrategys;
import com.carryxyh.tempdata.ConflictResultData;
import com.google.common.collect.Lists;
import javafx.util.Pair;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * LettuceChecker
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-09
 */
public final class LettuceChecker extends AbstractChecker<LettuceClient<?, ?, ?>> {

    private CheckStrategy<RedisCacheClient> checkStrategy;

    public LettuceChecker(TempDataDB tempDataDB,
                          LettuceClient<?, ?, ?> source,
                          LettuceClient<?, ?, ?> target) {
        super(tempDataDB, source, target);
    }

    @Override
    protected List<ConflictResultData> doCheck(List<Pair<String, String>> keys) {
        List<ConflictResultData> conflictData = Lists.newArrayList();
        for (Pair<String, String> p : keys) {
            String key = p.getKey();
            CheckResult check = checkStrategy.check(key, p.getValue());
            if (check instanceof DefaultCheckResult) {
                if (check.isConflict()) {
                    conflictData.add(toResult(check, key));
                }
            } else if (check instanceof RedisComplicitCheckResult) {
                List<CheckResult> checkResults = ((RedisComplicitCheckResult) check).sourceValue();
                if (CollectionUtils.isNotEmpty(checkResults)) {
                    for (CheckResult c : checkResults) {
                        if (c.isConflict()) {
                            conflictData.add(toResult(c, key));
                        }
                    }
                }
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
            this.checkStrategy = new RedisKeyExistCheckStrategy(source(), target());
        } else if (checkStrategys == CheckStrategys.VALUE_TYPE) {
            this.checkStrategy = new RedisValueTypeCheckStrategy(source(), target());
        } else if (checkStrategys == CheckStrategys.VALUE_EQUALS) {
            this.checkStrategy = new RedisTypeValueCheckStrategy(
                    source(),
                    target(),
                    checkerConfig.getComplicateThreshold(),
                    checkerConfig.getComplicateBatchCompareSize());
        } else {
            throw new IllegalArgumentException("can't match check strategy for : " + checkStrategys.name());
        }
    }
}
