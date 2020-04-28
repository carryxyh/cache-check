package com.carryxyh.client.redis.lettuce;

import com.carryxyh.client.redis.AbstractRedisCacheClient;
import com.carryxyh.client.redis.DefaultScanCursor;
import com.carryxyh.client.redis.MapValueAndCursor;
import com.carryxyh.client.redis.ScanArgs;
import com.carryxyh.client.redis.ScanCursor;
import com.carryxyh.client.redis.ScoreValueAndCursor;
import com.carryxyh.client.redis.StringValueAndCursor;
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
import java.util.Set;

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
    protected void doStop() {
        this.connection.close();
        this.connection = null;
        this.commands = null;
    }

    @Override
    protected void doClose() {
        this.client.shutdown();
        this.client = null;
    }

    @Override
    public String type(String key) {
        String type = commands.type(key);
        if (type == null) {
            type = ValueType.NONE.name();
        }
        return type;
    }

    @Override
    public String get(String key) {
        return commands.get(key);
    }

    @Override
    public StringValueAndCursor scan(ScanCursor cursor, ScanArgs scanArgs) {
        KeyScanCursor<String> scan;
        if (cursor == null) {
            scan =
                    commands.scan(io.lettuce.core.ScanCursor.INITIAL, buildArgs(scanArgs));

        } else {
            scan =
                    commands.scan(io.lettuce.core.ScanCursor.of(cursor.current()), buildArgs(scanArgs));
        }

        List<String> keys = scan.getKeys();
        return new StringValueAndCursor(new DefaultScanCursor(scan.getCursor(), scan.isFinished()), keys);
    }

    @Override
    public MapValueAndCursor hscan(String key, ScanCursor cursor, ScanArgs scanArgs) {
        MapScanCursor<String, String> scan;
        if (cursor == null) {
            scan =
                    commands.hscan(key, io.lettuce.core.ScanCursor.INITIAL, buildArgs(scanArgs));
        } else {
            scan =
                    commands.hscan(key, io.lettuce.core.ScanCursor.of(cursor.current()), buildArgs(scanArgs));
        }

        Map<String, String> values = scan.getMap();
        return new MapValueAndCursor(new DefaultScanCursor(scan.getCursor(), scan.isFinished()), values);
    }

    @Override
    public ScoreValueAndCursor zscan(String key, ScanCursor cursor, ScanArgs scanArgs) {
        ScoredValueScanCursor<String> scan;
        if (cursor == null) {
            scan =
                    commands.zscan(key, io.lettuce.core.ScanCursor.INITIAL, buildArgs(scanArgs));

        } else {
            scan =
                    commands.zscan(key, io.lettuce.core.ScanCursor.of(cursor.current()), buildArgs(scanArgs));
        }

        List<ScoredValue<String>> values = scan.getValues();
        List<Pair<String, Double>> l = parseFromScoreMember(values);
        return new ScoreValueAndCursor(new DefaultScanCursor(scan.getCursor(), scan.isFinished()), l);
    }

    @Override
    public StringValueAndCursor sscan(String key, ScanCursor cursor, ScanArgs scanArgs) {
        ValueScanCursor<String> scan;
        if (cursor == null) {
            scan =
                    commands.sscan(key, io.lettuce.core.ScanCursor.INITIAL, buildArgs(scanArgs));

        } else {
            scan =
                    commands.sscan(key, io.lettuce.core.ScanCursor.of(cursor.current()), buildArgs(scanArgs));
        }

        List<String> values = scan.getValues();
        return new StringValueAndCursor(new DefaultScanCursor(scan.getCursor(), scan.isFinished()), values);
    }

    @Override
    public List<String> lrange(String key, long start, long end) {
        return commands.lrange(key, start, end);
    }

    @Override
    public Long llen(String key) {
        return commands.llen(key);
    }

    @Override
    public Long hlen(String key) {
        return commands.hlen(key);
    }

    @Override
    public Long scard(String key) {
        return commands.scard(key);
    }

    @Override
    public Long zcard(String key) {
        return commands.zcard(key);
    }

    @Override
    public Map<String, String> hgetall(String key) {
        return commands.hgetall(key);
    }

    @Override
    public Set<String> smembers(String key) {
        return commands.smembers(key);
    }

    @Override
    public List<Pair<String, Double>> zrangewithscore(String key, long start, long end) {
        List<ScoredValue<String>> scoredValues = commands.zrangeWithScores(key, start, end);
        return parseFromScoreMember(scoredValues);
    }

    @Override
    public Boolean sismember(String key, String member) {
        return commands.sismember(key, member);
    }

    @Override
    public String hget(String key, String subKey) {
        return commands.hget(key, subKey);
    }

    @Override
    public Double zscore(String key, String member) {
        return commands.zscore(key, member);
    }

    // private methods ------------------------------------------------------------------------------------

    private static io.lettuce.core.ScanArgs buildArgs(ScanArgs scanArgs) {
        String match = scanArgs.match();
        if (match == null) {
            return io.lettuce.core.ScanArgs.Builder.limit(scanArgs.count());
        }
        return io.lettuce.core.ScanArgs.Builder.limit(scanArgs.count()).match(match);
    }

    private static List<Pair<String, Double>> parseFromScoreMember(List<ScoredValue<String>> values) {
        List<Pair<String, Double>> l = Lists.newArrayList();
        for (ScoredValue<String> s : values) {
            l.add(new Pair<>(s.getValue(), s.getScore()));
        }
        return l;
    }
}
