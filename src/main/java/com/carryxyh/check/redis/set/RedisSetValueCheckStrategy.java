package com.carryxyh.check.redis.set;

import com.carryxyh.CheckResult;
import com.carryxyh.DefaultCheckResult;
import com.carryxyh.check.redis.AbstractRedisComplicitStructureCheckStrategy;
import com.carryxyh.check.redis.RedisComplicitCheckResult;
import com.carryxyh.client.redis.DefaultScanArgs;
import com.carryxyh.client.redis.RedisCacheClient;
import com.carryxyh.client.redis.ScanArgs;
import com.carryxyh.client.redis.ScanCursor;
import com.carryxyh.client.redis.StringValueAndCursor;
import com.carryxyh.constants.ConflictType;
import com.carryxyh.constants.ValueType;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Set;

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

        List<CheckResult> checkResults = Lists.newArrayList();
        if (size > threshold) {
            // scan to compare.
            ScanArgs args = new DefaultScanArgs(batchCompareSize);
            StringValueAndCursor sscan =
                    source.sscan(key, null, args);
            List<String> values = sscan.values();
            if (CollectionUtils.isEmpty(values)) {
                return super.checkHoleKey(key);
            }

            for (String member : values) {
                CheckResult checkResult = checkSetMember(key, member);
                if (checkResult != null) {
                    checkResults.add(checkResult);
                }
            }

            ScanCursor cursor = sscan.cursor();
            while (!cursor.isFinished()) {
                StringValueAndCursor c = source.sscan(key, cursor, args);
                cursor = c.cursor();
                List<String> vs = c.values();
                for (String member : vs) {
                    CheckResult checkResult = checkSetMember(key, member);
                    if (checkResult != null) {
                        checkResults.add(checkResult);
                    }
                }
            }
        } else {
            Set<String> members = source.smembers(key);
            for (String member : members) {
                CheckResult checkResult = checkSetMember(key, member);
                if (checkResult != null) {
                    checkResults.add(checkResult);
                }
            }
        }
        return withResults(checkResults);
    }

    @Override
    protected RedisComplicitCheckResult checkMemberOrField(String key, String member) {
        return super.checkMemberOrField(key, member);
    }

    private CheckResult checkSetMember(String key, String sourceMember) {
        Boolean member = target.sismember(key, sourceMember);
        if (member == null || !member) {
            // can't reach here...
            return DefaultCheckResult.conflict(
                    ConflictType.LACK_FIELD_OR_MEMBER, ValueType.SET, sourceMember, null);
        } else {
            return null;
        }
    }
}
