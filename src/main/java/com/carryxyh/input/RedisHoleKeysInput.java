package com.carryxyh.input;

import com.carryxyh.KeysInput;
import com.carryxyh.client.redis.DefaultScanArgs;
import com.carryxyh.client.redis.RedisCacheClient;
import com.carryxyh.client.redis.ScanArgs;
import com.carryxyh.client.redis.ScanCursor;
import com.carryxyh.client.redis.StringValueAndCursor;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * RedisHoleKeysInput
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-24
 */
public class RedisHoleKeysInput implements KeysInput {

    private final RedisCacheClient redisCacheClient;

    private ScanCursor cursor;

    public RedisHoleKeysInput(RedisCacheClient redisCacheClient) {
        this.redisCacheClient = redisCacheClient;
    }

    /**
     * A lock is required to ensure synchronization since we should update global field {@link #cursor}.
     */
    @Override
    public synchronized List<String> input() {
        List<String> values = Lists.newArrayList();
        ScanArgs args = new DefaultScanArgs(30);
        if (cursor == null) {
            StringValueAndCursor scan = redisCacheClient.scan(null, args);
            this.cursor = scan.cursor();
            values.addAll(scan.values());
        }

        while (!cursor.isFinished()) {
            StringValueAndCursor c = redisCacheClient.scan(cursor, args);
            cursor = c.cursor();
            List<String> vs = c.values();
            if (CollectionUtils.isNotEmpty(vs)) {
                values.addAll(c.values());
            }
        }
        return values;
    }
}
