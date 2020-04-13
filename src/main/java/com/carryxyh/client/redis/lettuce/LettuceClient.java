package com.carryxyh.client.redis.lettuce;

import com.carryxyh.client.redis.AbstractRedisCacheClient;
import com.carryxyh.client.redis.DefaultScanCursor;
import com.carryxyh.client.redis.MapValueAndCursor;
import com.carryxyh.client.redis.ScanArgs;
import com.carryxyh.client.redis.ScanCursor;
import com.carryxyh.client.redis.ScoreValueAndCursor;
import com.carryxyh.client.redis.StringValueAndCursor;
import com.carryxyh.common.Command;
import com.carryxyh.common.StringResult;
import com.carryxyh.constants.ValueType;
import com.google.common.collect.Lists;
import io.lettuce.core.AbstractRedisClient;
import io.lettuce.core.KeyScanCursor;
import io.lettuce.core.MapScanCursor;
import io.lettuce.core.ScoredValue;
import io.lettuce.core.ScoredValueScanCursor;
import io.lettuce.core.ValueScanCursor;
import io.lettuce.core.api.StatefulConnection;
import io.lettuce.core.cluster.api.sync.RedisClusterCommands;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;

/**
 * LettuceClient
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public abstract class LettuceClient
        <C extends AbstractRedisClient,
                CN extends StatefulConnection<String, String>,
                CMD extends RedisClusterCommands<String, String>> extends AbstractRedisCacheClient {

    protected C client;

    protected CN connection;

    protected CMD commands;

    @Override
    protected void doStop() throws Exception {
        this.connection.close();
        this.connection = null;
        this.commands = null;
    }

    @Override
    protected void doClose() throws Exception {
        this.client.shutdown();
        this.client = null;
    }

    @Override
    public StringResult type(Command typeCmd) {
        String type = commands.type(typeCmd.key());
        if (type == null) {
            type = ValueType.NONE.name();
        }
        return new StringResult(type);
    }

    @Override
    public StringResult get(Command getCmd) {
        return new StringResult(commands.get(getCmd.key()));
    }

    @Override
    public StringValueAndCursor scan(ScanCursor cursor, ScanArgs scanArgs) {
        KeyScanCursor<String> scan;
        if (cursor == null) {
            scan =
                    commands.scan(io.lettuce.core.ScanCursor.INITIAL,
                            io.lettuce.core.ScanArgs.Builder.limit(scanArgs.count()).match(scanArgs.match()));

        } else {
            scan =
                    commands.scan(io.lettuce.core.ScanCursor.of(cursor.current()),
                            io.lettuce.core.ScanArgs.Builder.limit(scanArgs.count()).match(scanArgs.match()));
        }

        List<String> keys = scan.getKeys();
        return new StringValueAndCursor(new DefaultScanCursor(scan.getCursor(), scan.isFinished()), keys);
    }

    @Override
    public MapValueAndCursor hscan(String key, ScanCursor cursor, ScanArgs scanArgs) {
        MapScanCursor<String, String> scan;
        if (cursor == null) {
            scan =
                    commands.hscan(key, io.lettuce.core.ScanCursor.INITIAL,
                            io.lettuce.core.ScanArgs.Builder.limit(scanArgs.count()).match(scanArgs.match()));
        } else {
            scan =
                    commands.hscan(key, io.lettuce.core.ScanCursor.of(cursor.current()),
                            io.lettuce.core.ScanArgs.Builder.limit(scanArgs.count()).match(scanArgs.match()));
        }

        Map<String, String> values = scan.getMap();
        return new MapValueAndCursor(new DefaultScanCursor(scan.getCursor(), scan.isFinished()), values);
    }

    @Override
    public ScoreValueAndCursor zscan(String key, ScanCursor cursor, ScanArgs scanArgs) {
        ScoredValueScanCursor<String> scan;
        if (cursor == null) {
            scan =
                    commands.zscan(key, io.lettuce.core.ScanCursor.INITIAL,
                            io.lettuce.core.ScanArgs.Builder.limit(scanArgs.count()).match(scanArgs.match()));

        } else {
            scan =
                    commands.zscan(key, io.lettuce.core.ScanCursor.of(cursor.current()),
                            io.lettuce.core.ScanArgs.Builder.limit(scanArgs.count()).match(scanArgs.match()));
        }

        List<ScoredValue<String>> values = scan.getValues();
        List<Pair<String, Double>> l = Lists.newArrayList();
        for (ScoredValue<String> s : values) {
            l.add(new Pair<>(s.getValue(), s.getScore()));
        }
        return new ScoreValueAndCursor(new DefaultScanCursor(scan.getCursor(), scan.isFinished()), l);
    }

    @Override
    public StringValueAndCursor sscan(String key, ScanCursor cursor, ScanArgs scanArgs) {
        ValueScanCursor<String> scan;
        if (cursor == null) {
            scan =
                    commands.sscan(key, io.lettuce.core.ScanCursor.INITIAL,
                            io.lettuce.core.ScanArgs.Builder.limit(scanArgs.count()).match(scanArgs.match()));

        } else {
            scan =
                    commands.sscan(key, io.lettuce.core.ScanCursor.of(cursor.current()),
                            io.lettuce.core.ScanArgs.Builder.limit(scanArgs.count()).match(scanArgs.match()));
        }

        List<String> values = scan.getValues();
        return new StringValueAndCursor(new DefaultScanCursor(scan.getCursor(), scan.isFinished()), values);
    }

    @Override
    public List<String> lrange(String key, long start, long end) {
        return commands.lrange(key, start, end);
    }
}
