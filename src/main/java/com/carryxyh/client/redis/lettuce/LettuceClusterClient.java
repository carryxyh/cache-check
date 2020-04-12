package com.carryxyh.client.redis.lettuce;

import com.carryxyh.config.ClientConfig;
import com.carryxyh.config.Config;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;

/**
 * LettuceClusterClient
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-12
 */
public class LettuceClusterClient extends LettuceClient<RedisClusterClient,
        StatefulRedisClusterConnection<String, String>,
        RedisAdvancedClusterCommands<String, String>> {

    @Override
    protected void doInit(Config config) throws Exception {
        ClientConfig clientConfig = (ClientConfig) config;
        String host = clientConfig.getHost();
        int port = clientConfig.getPort();
        String password = clientConfig.getPassword();

        this.client = RedisClusterClient.create("redis://" +
                password + "@" + host + ":" + port);
    }

    @Override
    protected void doStart() throws Exception {
        this.connection = client.connect();
        this.commands = connection.sync();
    }
}
