package com.carryxyh.client.redis;

import com.carryxyh.CacheClient;
import com.carryxyh.common.Command;
import com.carryxyh.common.StringResult;

/**
 * RedisCacheClient
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public interface RedisCacheClient extends CacheClient {

    StringResult type(Command typeCmd);

    @Override
    StringResult get(Command typeCmd);
}
