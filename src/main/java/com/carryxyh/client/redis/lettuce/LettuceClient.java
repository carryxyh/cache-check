package com.carryxyh.client.redis.lettuce;

import com.carryxyh.client.redis.AbstractRedisCacheClient;
import com.carryxyh.common.Command;
import com.carryxyh.common.StringResult;
import com.carryxyh.constants.ValueType;
import io.lettuce.core.AbstractRedisClient;
import io.lettuce.core.api.StatefulConnection;
import io.lettuce.core.cluster.api.sync.RedisClusterCommands;

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
}
