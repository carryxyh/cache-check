package com.carryxyh.client.redis.lettuce;

import com.carryxyh.config.ClientConfig;
import com.carryxyh.config.ClientInfo;
import com.carryxyh.config.Config;
import com.carryxyh.constants.CacheClusterMode;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * LettuceStandaloneClient
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-12
 */
public class LettuceStandaloneClient extends LettuceClient<RedisClient, StatefulRedisConnection<String, String>,
        RedisCommands<String, String>> {

    @Override
    protected void doInit(Config config) {
        ClientConfig clientConfig = (ClientConfig) config;
        List<ClientInfo> clientInfos = clientConfig.getClientInfos();
        String password = clientConfig.getPassword();
        CacheClusterMode cacheClusterMode = clientConfig.getCacheClusterMode();

        // build.
        StringBuilder urlBuilder = new StringBuilder();
        if (cacheClusterMode == CacheClusterMode.STANDALONE) {
            urlBuilder.append("redis://");
        } else if (cacheClusterMode == CacheClusterMode.SENTINEL) {
            urlBuilder.append("redis-sentinel://");
        }
        if (StringUtils.isNotBlank(password)) {
            urlBuilder.append(password).append("@");
        }
        for (int i = 0; i < clientInfos.size(); i++) {
            ClientInfo c = clientInfos.get(i);
            urlBuilder.append(c.getHost()).append(":").append(c.getPort());
            if (i != clientInfos.size() - 1) {
                urlBuilder.append(",");
            }
        }
        this.client = RedisClient.create(urlBuilder.toString());
    }

    @Override
    protected void doStart() {
        this.connection = client.connect();
        this.commands = this.connection.sync();
    }
}
