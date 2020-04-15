package com.carryxyh.check.redis.set;

import com.carryxyh.CheckResult;
import com.carryxyh.DefaultCheckResult;
import com.carryxyh.check.redis.AbstractRedisComplicitStructureCheckStrategy;
import com.carryxyh.check.redis.RedisComplicitCheckResult;
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
public class RedisSetValueCheckStrategy extends AbstractRedisComplicitStructureCheckStrategy {

    public RedisSetValueCheckStrategy(RedisCacheClient source, RedisCacheClient target, int threshold, int batchCompareSize) {
        super(source, target, threshold, batchCompareSize);
    }

    @Override
    protected RedisComplicitCheckResult checkHoleKey(String key) {
        Long size = source.scard(key);
        if (size == null) {
            // key not exists.
            return super.checkHoleKey(key);
        }

        if (size > threshold) {
            // scan to compare.
            StringValueAndCursor sscan =
                    source.sscan(key, null, new DefaultScanArgs(batchCompareSize));

            ScanCursor cursor = sscan.cursor();
            List<String> values = sscan.values();


        } else {

        }
        return super.checkHoleKey(key);
    }

    @Override
    protected RedisComplicitCheckResult checkMemberOrField(String key, String member) {
        return super.checkMemberOrField(key, member);
    }
}
