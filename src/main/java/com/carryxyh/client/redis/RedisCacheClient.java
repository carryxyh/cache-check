package com.carryxyh.client.redis;

import com.carryxyh.CacheClient;
import com.carryxyh.common.Command;
import com.carryxyh.common.StringResult;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * RedisCacheClient
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public interface RedisCacheClient extends CacheClient {

    // basic ------------------------------------------------------------------------------------

    StringResult type(Command typeCmd);

    @Override
    StringResult get(Command getCmd);

    // scan ------------------------------------------------------------------------------------

    StringValueAndCursor scan(ScanCursor cursor, ScanArgs scanArgs);

    MapValueAndCursor hscan(String key, ScanCursor cursor, ScanArgs scanArgs);

    ScoreValueAndCursor zscan(String key, ScanCursor cursor, ScanArgs scanArgs);

    StringValueAndCursor sscan(String key, ScanCursor cursor, ScanArgs scanArgs);

    List<String> lrange(String key, long start, long end);

    // length ------------------------------------------------------------------------------------

    Long llen(String key);

    Long hlen(String key);

    Long scard(String key);

    Long zcard(String key);

    // get all ------------------------------------------------------------------------------------

    Map<String, String> hgetall(String key);

    Set<String> smembers(String key);

    List<Pair<String, Double>> zrangewithscore(String key, long start, long end);
}
