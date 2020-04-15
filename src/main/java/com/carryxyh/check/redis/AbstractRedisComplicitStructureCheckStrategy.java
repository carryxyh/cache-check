package com.carryxyh.check.redis;

import com.carryxyh.check.AbstractCheckStrategy;
import com.carryxyh.client.redis.RedisCacheClient;

/**
 * AbstractRedisComplicitStructureCheckStrategy
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-13
 */
public abstract class AbstractRedisComplicitStructureCheckStrategy extends AbstractCheckStrategy<RedisCacheClient> {

    protected final int threshold;

    protected final int batchCompareSize;

    public AbstractRedisComplicitStructureCheckStrategy(RedisCacheClient source, RedisCacheClient target,
                                                        int threshold, int batchCompareSize) {
        super(source, target);
        this.threshold = threshold;
        this.batchCompareSize = batchCompareSize;
    }

    @Override
    public RedisComplicitCheckResult check(String key, String subKey) {
        if (subKey == null) {
            return checkHoleKey(key);
        }
        return checkMemberOrField(key, subKey);
    }

    protected RedisComplicitCheckResult checkHoleKey(String key) {
        return RedisComplicitCheckResult.nonConflict();
    }

    protected RedisComplicitCheckResult checkMemberOrField(String key, String member) {
        return RedisComplicitCheckResult.nonConflict();
    }
}
