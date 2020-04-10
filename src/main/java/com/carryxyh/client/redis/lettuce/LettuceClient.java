package com.carryxyh.client.redis.lettuce;

import com.carryxyh.client.redis.AbstractRedisCacheClient;
import com.carryxyh.common.Command;
import com.carryxyh.common.StringResult;
import com.carryxyh.config.ClientConfig;
import com.carryxyh.config.Config;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;

/**
 * LettuceClient
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public final class LettuceClient extends AbstractRedisCacheClient {

    private RedisClusterClient redisClusterClient;

    private StatefulRedisClusterConnection<String, String> connection;

    private RedisAdvancedClusterCommands<String, String> commands;

    @Override
    protected void doInit(Config config) throws Exception {
        ClientConfig clientConfig = (ClientConfig) config;
        String host = clientConfig.getHost();
        int port = clientConfig.getPort();
        String password = clientConfig.getPassword();

        this.redisClusterClient = RedisClusterClient.create("redis://" +
                password + "@" + host + ":" + port);
    }

    @Override
    protected void doStart() throws Exception {
        this.connection = redisClusterClient.connect();
        this.commands = connection.sync();
    }

    @Override
    protected void doStop() throws Exception {
        this.connection.close();
        this.connection = null;
        this.commands = null;
    }

    @Override
    protected void doClose() throws Exception {
        this.redisClusterClient.shutdown();
        this.redisClusterClient = null;
    }

    @Override
    public StringResult type(Command typeCmd) {
        return null;
    }

    @Override
    public StringResult get(Command getCmd) {
        return null;
    }
}
