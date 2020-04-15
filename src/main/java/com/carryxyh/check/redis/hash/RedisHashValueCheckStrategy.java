package com.carryxyh.check.redis.hash;

import com.carryxyh.CheckResult;
import com.carryxyh.DefaultCheckResult;
import com.carryxyh.check.redis.AbstractRedisComplicitStructureCheckStrategy;
import com.carryxyh.check.redis.RedisComplicitCheckResult;
import com.carryxyh.client.redis.DefaultScanArgs;
import com.carryxyh.client.redis.MapValueAndCursor;
import com.carryxyh.client.redis.RedisCacheClient;
import com.carryxyh.client.redis.ScanArgs;
import com.carryxyh.client.redis.ScanCursor;
import com.carryxyh.constants.ConflictType;
import com.carryxyh.constants.ValueType;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * RedisHashValueCheckStrategy
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-12
 */
public class RedisHashValueCheckStrategy extends AbstractRedisComplicitStructureCheckStrategy {

    public RedisHashValueCheckStrategy(RedisCacheClient source, RedisCacheClient target,
                                       int threshold, int batchCompareSize) {
        super(source, target, threshold, batchCompareSize);
    }

    @Override
    protected RedisComplicitCheckResult checkHoleKey(String key) {
        Long length = source.hlen(key);
        if (length == null || length <= 0) {
            return super.checkHoleKey(key);
        }

        List<CheckResult> checkResults = Lists.newArrayList();
        if (length > threshold) {
            // scan to compare.
            ScanArgs args = new DefaultScanArgs(batchCompareSize);
            MapValueAndCursor hscan = source.hscan(key, null, args);
            Map<String, String> values = hscan.values();
            if (values == null || values.size() <= 0) {
                return super.checkHoleKey(key);
            }

            for (Map.Entry<String, String> entry : values.entrySet()) {
                CheckResult checkResult = checkHashMember(key, entry.getKey(), entry.getValue());
                if (checkResult != null) {
                    checkResults.add(checkResult);
                }
            }

            ScanCursor cursor = hscan.cursor();
            while (!cursor.isFinished()) {
                MapValueAndCursor c = source.hscan(key, cursor, args);
                cursor = c.cursor();
                Map<String, String> vs = c.values();
                for (Map.Entry<String, String> entry : vs.entrySet()) {
                    CheckResult checkResult = checkHashMember(key, entry.getKey(), entry.getValue());
                    if (checkResult != null) {
                        checkResults.add(checkResult);
                    }
                }
            }

        } else {
            Map<String, String> all = source.hgetall(key);
            if (all == null || all.size() <= 0L) {
                return super.checkHoleKey(key);
            }

            for (Map.Entry<String, String> entry : all.entrySet()) {
                CheckResult checkResult = checkHashMember(key, entry.getKey(), entry.getValue());
                if (checkResult != null) {
                    checkResults.add(checkResult);
                }
            }
        }

        return withResults(checkResults);
    }

    @Override
    protected RedisComplicitCheckResult checkMemberOrField(String key, String member) {
        return null;
    }

    private CheckResult checkHashMember(String key, String subKey, String sourceValue) {
        String targetValue = target.hget(key, subKey);
        if (targetValue == null) {
            return DefaultCheckResult.conflict(
                    ConflictType.LACK_FIELD_OR_MEMBER, ValueType.HASH, subKey, null);
        } else {
            if (StringUtils.equals(sourceValue, targetValue)) {
                return null;
            } else {
                return DefaultCheckResult.conflict(
                        ConflictType.FIELD_OR_MEMBER_VALUE, ValueType.HASH, subKey, sourceValue, targetValue);
            }
        }
    }
}
