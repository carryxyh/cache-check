package com.carryxyh.client.redis;

import com.carryxyh.client.AbstractCacheClient;
import com.carryxyh.common.Command;
import com.carryxyh.common.DefaultResult;
import com.carryxyh.common.Result;

/**
 * AbstractRedisCacheClient
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public abstract class AbstractRedisCacheClient extends AbstractCacheClient implements RedisCacheClient {

    @Override
    public Result get(Command getCmd) {
        return DefaultResult.valueOf(doGet(getCmd.key()));
    }

    protected abstract String doGet(String key);
}
