package com.carryxyh.client.redis;

import com.carryxyh.CacheClient;
import com.carryxyh.common.Command;
import com.carryxyh.common.StringResult;

import java.util.List;

/**
 * RedisCacheClient
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public interface RedisCacheClient extends CacheClient {

    StringResult type(Command typeCmd);

    @Override
    StringResult get(Command getCmd);

    StringValueAndCursor scan(ScanCursor cursor, ScanArgs scanArgs);

    MapValueAndCursor hscan(String key, ScanCursor cursor, ScanArgs scanArgs);

    ScoreValueAndCursor zscan(String key, ScanCursor cursor, ScanArgs scanArgs);

    StringValueAndCursor sscan(String key, ScanCursor cursor, ScanArgs scanArgs);

    List<String> lrange(String key, long start, long end);
}
