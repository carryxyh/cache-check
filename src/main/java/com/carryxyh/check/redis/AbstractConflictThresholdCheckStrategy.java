package com.carryxyh.check.redis;

import com.carryxyh.CheckResult;
import com.carryxyh.check.AbstractCheckStrategy;
import com.carryxyh.client.redis.RedisCacheClient;

/**
 * AbstractConflictThresholdCheckStrategy
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-13
 */
public abstract class AbstractConflictThresholdCheckStrategy extends AbstractCheckStrategy<RedisCacheClient> {

    protected final int threshold;

    protected final int batchCompareSize;

    public AbstractConflictThresholdCheckStrategy(RedisCacheClient source, RedisCacheClient target,
                                                  int threshold, int batchCompareSize) {

        super(source, target);
        this.threshold = threshold;
        this.batchCompareSize = batchCompareSize;
    }

    @Override
    public CheckResult check(String key, String subKey) {
        if (subKey == null) {
            return checkHoleKey(key);
        }
        return checkMemberOrField(key, subKey);
    }

    protected CheckResult checkHoleKey(String key) {
        return null;
    }

    protected CheckResult checkMemberOrField(String key, String member) {
        return null;
    }
}
