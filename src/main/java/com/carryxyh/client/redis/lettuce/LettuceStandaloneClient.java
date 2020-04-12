package com.carryxyh.client.redis.lettuce;

import com.carryxyh.config.ClientConfig;
import com.carryxyh.config.Config;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

/**
 * LettuceStandaloneClient
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-12
 */
public class LettuceStandaloneClient extends LettuceClient<RedisClient, StatefulRedisConnection<String, String>,
        RedisCommands<String, String>> {

    @Override
    protected void doInit(Config config) throws Exception {
        ClientConfig clientConfig = (ClientConfig) config;
        String host = clientConfig.getHost();
        int port = clientConfig.getPort();
        String password = clientConfig.getPassword();

        this.client = RedisClient.create("redis://" +
                password + "@" + host + ":" + port);
    }

    @Override
    protected void doStart() throws Exception {
        this.connection = client.connect();
        this.commands = this.connection.sync();
    }
}
