package com.carryxyh;

import com.carryxyh.config.CheckerConfig;
import com.carryxyh.config.ClientConfig;
import com.carryxyh.config.InputOutputConfig;
import com.carryxyh.config.TempDBConfig;
import com.carryxyh.constants.CacheClients;
import com.carryxyh.constants.CacheClusterMode;
import com.carryxyh.constants.CacheType;

/**
 * Runner
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-23
 */
public final class Runner implements Runnable {

    private final CheckerConfig checkerConfig;

    private final ClientConfig source;

    private final ClientConfig target;

    private final InputOutputConfig input;

    private final InputOutputConfig output;

    private final TempDBConfig tempDB;

    public Runner(CheckerConfig checkerConfig,
                  ClientConfig source,
                  ClientConfig target,
                  InputOutputConfig input,
                  InputOutputConfig output,
                  TempDBConfig tempDB) {
        this.checkerConfig = checkerConfig;
        this.source = source;
        this.target = target;
        this.input = input;
        this.output = output;
        this.tempDB = tempDB;
    }

    @Override
    public void run() {

        // build source. ----------------------------------------------------------------------------------------------

        // build target. ----------------------------------------------------------------------------------------------

    }

    private <T extends CacheClient> T buildCacheClient(ClientConfig clientConfig) {
        CacheType cacheType = clientConfig.getCacheType();
        CacheClients cacheClient = clientConfig.getCacheClient();
        if (cacheType == CacheType.REDIS) {
            if (cacheClient == CacheClients.LETTUCE) {
                CacheClusterMode cacheClusterMode = clientConfig.getCacheClusterMode();

            } else {
                throw new IllegalArgumentException("can't match cache client for redis : " + cacheClient.name());
            }
        } else if (cacheType == CacheType.MEMCACHE) {
            if (cacheClient == CacheClients.XMEMCACHE) {

            } else {
                throw new IllegalArgumentException("can't match cache client for memcache : " + cacheClient.name());
            }
        }
        return null;
    }
}
