package com.carryxyh.check.redis.set;

import com.carryxyh.CheckResult;
import com.carryxyh.DefaultCheckResult;
import com.carryxyh.check.redis.AbstractConflictThresholdCheckStrategy;
import com.carryxyh.client.redis.DefaultScanArgs;
import com.carryxyh.client.redis.RedisCacheClient;
import com.carryxyh.client.redis.ScanCursor;
import com.carryxyh.client.redis.StringValueAndCursor;

import java.util.List;

/**
 * RedisSetValueCheckStrategy
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-12
 */
public class RedisSetValueCheckStrategy extends AbstractConflictThresholdCheckStrategy {

    public RedisSetValueCheckStrategy(RedisCacheClient source, RedisCacheClient target, int threshold, int batchCompareSize) {
        super(source, target, threshold, batchCompareSize);
    }

    @Override
    public CheckResult check(String key, String subKey) {
        Long size = source.scard(key);
        if (size == null) {
            // key not exists.
            return DefaultCheckResult.nonConflict();
        }

        if (size > threshold) {
            // scan to compare.
            StringValueAndCursor sscan =
                    source.sscan(key, null, new DefaultScanArgs(batchCompareSize));

            ScanCursor cursor = sscan.cursor();
            List<String> values = sscan.values();


        } else {

        }
        return null;
    }
}
