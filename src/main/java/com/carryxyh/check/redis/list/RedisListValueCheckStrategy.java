package com.carryxyh.check.redis.list;

import com.carryxyh.CheckResult;
import com.carryxyh.DefaultCheckResult;
import com.carryxyh.check.redis.AbstractRedisComplicitStructureCheckStrategy;
import com.carryxyh.check.redis.RedisComplicitCheckResult;
import com.carryxyh.client.redis.RedisCacheClient;
import com.carryxyh.constants.ConflictType;
import com.carryxyh.constants.ValueType;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * RedisListValueCheckStrategy
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-12
 */
public class RedisListValueCheckStrategy extends AbstractRedisComplicitStructureCheckStrategy {

    public RedisListValueCheckStrategy(RedisCacheClient source, RedisCacheClient target,
                                       int threshold, int batchCompareSize) {
        super(source, target, threshold, batchCompareSize);
    }

    @Override
    public RedisComplicitCheckResult check(String key, String subKey) {
        Long length = source.llen(key);
        if (length == null || length <= 0L) {
            return RedisComplicitCheckResult.nonConflict();
        }

        if (length > threshold) {
            // scan to compare.
            List<CheckResult> results = Lists.newArrayList();
            long step = length > batchCompareSize ? batchCompareSize : length;
            long start = 0;
            long end = start + step;

            while (end < length) {
                List<String> sourceValue = source.lrange(key, start, end);
                List<String> targetValue = target.lrange(key, start, end);
                List<CheckResult> compare = compare(sourceValue, targetValue, start);
                if (CollectionUtils.isNotEmpty(compare)) {
                    results.addAll(compare);
                }
                start += batchCompareSize + 1;
                end = start + batchCompareSize;
            }

            return withResults(results);
        } else {
            // redis scan use `[start, end]`, which is not same with java.
            long scanSize = length - 1;
            List<String> sourceValue = source.lrange(key, 0, scanSize);
            List<String> targetValue = target.lrange(key, 0, scanSize);
            return withResults(compare(sourceValue, targetValue, 0));
        }
    }

    private List<CheckResult> compare(List<String> sourceValue, List<String> targetValue, long scanShift) {
        List<CheckResult> results = Lists.newArrayList();
        for (int i = 0; i < sourceValue.size(); i++) {
            String sourceMember = sourceValue.get(i);
            if (i >= targetValue.size()) {
                results.add(DefaultCheckResult.conflict(
                        ConflictType.LACK_FIELD_OR_MEMBER, ValueType.LIST, sourceMember, null));
            } else {
                String targetMember = targetValue.get(i);
                if (!StringUtils.equals(sourceMember, targetMember)) {
                    results.add(DefaultCheckResult.conflict(
                            ConflictType.FIELD_OR_MEMBER_VALUE, ValueType.LIST,
                            i + scanShift, i + scanShift));
                }
            }
        }
        return results;
    }
}
