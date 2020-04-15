package com.carryxyh.check.redis.zset;

import com.carryxyh.CheckResult;
import com.carryxyh.DefaultCheckResult;
import com.carryxyh.check.redis.AbstractRedisComplicitStructureCheckStrategy;
import com.carryxyh.check.redis.RedisComplicitCheckResult;
import com.carryxyh.client.redis.DefaultScanArgs;
import com.carryxyh.client.redis.RedisCacheClient;
import com.carryxyh.client.redis.ScanArgs;
import com.carryxyh.client.redis.ScanCursor;
import com.carryxyh.client.redis.ScoreValueAndCursor;
import com.carryxyh.constants.ConflictType;
import com.google.common.collect.Lists;
import javafx.util.Pair;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * RedisSortedSetValueCheckStrategy
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-12
 */
public class RedisSortedSetValueCheckStrategy extends AbstractRedisComplicitStructureCheckStrategy {

    public RedisSortedSetValueCheckStrategy(RedisCacheClient source, RedisCacheClient target,
                                            int threshold, int batchCompareSize) {
        super(source, target, threshold, batchCompareSize);
    }

    @Override
    protected RedisComplicitCheckResult checkHoleKey(String key) {
        Long size = source.zcard(key);
        if (size == null || size <= 0L) {
            // key not exists.
            return RedisComplicitCheckResult.nonConflict();
        }

        List<CheckResult> checkResults = Lists.newArrayList();
        if (size > threshold) {
            // scan to compare.
            ScanArgs args = new DefaultScanArgs(batchCompareSize);
            ScoreValueAndCursor zscan = source.zscan(key, null, args);
            List<Pair<String, Double>> values = zscan.values();
            if (CollectionUtils.isEmpty(values)) {
                return RedisComplicitCheckResult.nonConflict();
            }

            for (Pair<String, Double> p : values) {
                CheckResult checkResult = checkMember(key, p);
                if (checkResult != null) {
                    checkResults.add(checkResult);
                }
            }

            ScanCursor cursor = zscan.cursor();
            while (!cursor.isFinished()) {
                ScoreValueAndCursor c = source.zscan(key, cursor, args);
                cursor = c.cursor();
                List<Pair<String, Double>> vs = c.values();
                for (Pair<String, Double> p : vs) {
                    CheckResult checkResult = checkMember(key, p);
                    if (checkResult != null) {
                        checkResults.add(checkResult);
                    }
                }
            }
        } else {
            List<Pair<String, Double>> sourceValues = source.zrangewithscore(key, 0L, -1L);
            if (CollectionUtils.isEmpty(sourceValues)) {
                return RedisComplicitCheckResult.nonConflict();
            }
            for (Pair<String, Double> p : sourceValues) {
                CheckResult checkResult = checkMember(key, p);
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

    private CheckResult checkMember(String key, Pair<String, Double> p) {
        // source.
        String member = p.getKey();
        Double sourceScore = p.getValue();

        // target.
        Double targetScore = target.zscore(key, member);
        if (targetScore == null) {
            return DefaultCheckResult.conflict(ConflictType.LACK_FIELD_OR_MEMBER, member, null);
        } else {
            if (targetScore.equals(sourceScore)) {
                return null;
            } else {
                return DefaultCheckResult.conflict(
                        ConflictType.FIELD_OR_MEMBER_VALUE, member, sourceScore, targetScore);
            }
        }
    }
}
