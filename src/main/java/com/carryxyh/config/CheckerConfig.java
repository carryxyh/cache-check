package com.carryxyh.config;

import com.carryxyh.CacheClient;
import com.carryxyh.Checker;
import com.carryxyh.TempDataDB;
import com.carryxyh.check.memcache.XMemcacheChecker;
import com.carryxyh.check.redis.LettuceChecker;
import com.carryxyh.client.memcache.xmemcache.XMemcacheClient;
import com.carryxyh.client.redis.lettuce.LettuceClient;
import com.carryxyh.constants.CacheClients;
import com.carryxyh.constants.CacheType;
import com.carryxyh.constants.CheckStrategys;

/**
 * CheckerConfig
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public class CheckerConfig extends AbstractConfig {

    private static final long serialVersionUID = -5171605193581357788L;

    private CheckStrategys checkStrategys = CheckStrategys.VALUE_EQUALS;

    private int parallel = 10;

    private int rounds = 3;

    private long internal = 60L * 1000L;

    private int complicateThreshold = 256;

    private int complicateBatchCompareSize = 30;

    public int getComplicateThreshold() {
        return complicateThreshold;
    }

    public void setComplicateThreshold(int complicateThreshold) {
        this.complicateThreshold = complicateThreshold;
    }

    public int getComplicateBatchCompareSize() {
        return complicateBatchCompareSize;
    }

    public void setComplicateBatchCompareSize(int complicateBatchCompareSize) {
        this.complicateBatchCompareSize = complicateBatchCompareSize;
    }

    public CheckStrategys getCheckStrategys() {
        return checkStrategys;
    }

    public void setCheckStrategys(CheckStrategys checkStrategys) {
        this.checkStrategys = checkStrategys;
    }

    public int getParallel() {
        return parallel;
    }

    public void setParallel(int parallel) {
        this.parallel = parallel;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        if (rounds < 1) {
            throw new  IllegalArgumentException("rounds should >= 1.");
        }
        this.rounds = rounds;
    }

    public long getInternal() {
        return internal;
    }

    public void setInternal(long internal) {
        this.internal = internal;
    }

    @SuppressWarnings("rawtypes, unchecked")
    public <T extends CacheClient> Checker<T> buildChecker(CacheType cacheType,
                                                           CacheClients cacheClient,
                                                           TempDataDB tempDataDB,
                                                           CacheClient source,
                                                           CacheClient target) {
        if (cacheType == CacheType.REDIS) {
            if (cacheClient == CacheClients.LETTUCE) {
                LettuceChecker checker = new LettuceChecker(tempDataDB, (LettuceClient) source, (LettuceClient) target);
                checker.init(this);
                return (Checker<T>) checker;
            } else {
                throw new IllegalArgumentException("can't match cache client for redis : " + cacheClient.name());
            }
        } else if (cacheType == CacheType.MEMCACHE) {
            if (cacheClient == CacheClients.XMEMCACHE) {
                XMemcacheChecker checker = new XMemcacheChecker(tempDataDB, (XMemcacheClient) source, (XMemcacheClient) target);
                checker.init(this);
                return (Checker<T>) checker;
            } else {
                throw new IllegalArgumentException("can't match cache client for redis : " + cacheClient.name());
            }
        } else {
            throw new IllegalArgumentException("can't match cache type for : " + cacheType.name());
        }
    }
}
