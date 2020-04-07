package com.carryxyh.client;

import com.carryxyh.lifecycle.Endpoint;

/**
 * AbstractCacheClient
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public abstract class AbstractCacheClient extends Endpoint implements CacheClient {

    protected final ClientConfig clientConfig;

    protected AbstractCacheClient(ClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }
}
