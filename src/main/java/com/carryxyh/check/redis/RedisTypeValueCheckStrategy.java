package com.carryxyh.check.redis;

import com.carryxyh.CheckResult;
import com.carryxyh.DefaultCheckResult;
import com.carryxyh.check.redis.hash.RedisHashValueCheckStrategy;
import com.carryxyh.check.redis.list.RedisListValueCheckStrategy;
import com.carryxyh.check.redis.set.RedisSetValueCheckStrategy;
import com.carryxyh.check.redis.string.RedisStringValueCheckStrategy;
import com.carryxyh.check.redis.zset.RedisSortedSetValueCheckStrategy;
import com.carryxyh.client.redis.RedisCacheClient;
import com.carryxyh.constants.ValueType;
import org.apache.commons.lang3.StringUtils;

/**
 * RedisTypeValueCheckStrategy
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-12
 */
class RedisTypeValueCheckStrategy extends RedisValueTypeCheckStrategy {

    private final RedisStringValueCheckStrategy stringValueCheckStrategy;

    private final RedisSortedSetValueCheckStrategy sortedSetValueCheckStrategy;

    private final RedisSetValueCheckStrategy setValueCheckStrategy;

    private final RedisHashValueCheckStrategy hashValueCheckStrategy;

    private final RedisListValueCheckStrategy listValueCheckStrategy;

    public RedisTypeValueCheckStrategy(RedisCacheClient source,
                                       RedisCacheClient target,
                                       int threshold,
                                       int batchCompareSize) {
        super(source, target);
        this.stringValueCheckStrategy = new RedisStringValueCheckStrategy(source, target);

        this.sortedSetValueCheckStrategy = new RedisSortedSetValueCheckStrategy(
                source, target, threshold, batchCompareSize);

        this.setValueCheckStrategy = new RedisSetValueCheckStrategy(source, target, threshold, batchCompareSize);
        this.hashValueCheckStrategy = new RedisHashValueCheckStrategy(source, target, threshold, batchCompareSize);
        this.listValueCheckStrategy = new RedisListValueCheckStrategy(source, target, threshold, batchCompareSize);
    }

    @Override
    public CheckResult check(String key, String subKey) {
        CheckResult check = super.check(key, subKey);
        if (check.isConflict()) {
            return check;
        }

        // type must be same when run here.
        String sourceType = source.type(key);
        String targetType = target.type(key);
        if (!StringUtils.equals(sourceType, targetType)) {
            throw new IllegalStateException("source type and target type is not equals, this must be a bug!");
        }

        if (ValueType.STRING.name().equalsIgnoreCase(sourceType)) {
            return stringValueCheckStrategy.check(key, subKey);
        } else if (ValueType.LIST.name().equalsIgnoreCase(sourceType)) {
            return listValueCheckStrategy.check(key, subKey);
        } else if (ValueType.SET.name().equalsIgnoreCase(sourceType)) {
            return setValueCheckStrategy.check(key, subKey);
        } else if (ValueType.HASH.name().equalsIgnoreCase(sourceType)) {
            return hashValueCheckStrategy.check(key, subKey);
        } else if (ValueType.ZSET.name().equalsIgnoreCase(sourceType)) {
            return sortedSetValueCheckStrategy.check(key, subKey);
        } else if (ValueType.STREAM.name().equalsIgnoreCase(sourceType)) {
            // TODO not support stream compare yet...
            return DefaultCheckResult.nonConflict();
        } else {
            throw new IllegalStateException("can't match source type for " + sourceType);
        }
    }
}
