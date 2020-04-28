package com.carryxyh.client.redis.lettuce;

import com.carryxyh.config.ClientConfig;
import com.carryxyh.config.ClientInfo;
import com.carryxyh.config.Config;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

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
    protected void doInit(Config config) {
        ClientConfig clientConfig = (ClientConfig) config;
        List<ClientInfo> clientInfos = clientConfig.getClientInfos();
        String password = clientConfig.getPassword();

        // build.
        StringBuilder urlBuilder = new StringBuilder();
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
        this.client = RedisClusterClient.create(urlBuilder.toString());
    }

    @Override
    protected void doStart() {
        this.connection = client.connect();
        this.commands = connection.sync();
    }
}
