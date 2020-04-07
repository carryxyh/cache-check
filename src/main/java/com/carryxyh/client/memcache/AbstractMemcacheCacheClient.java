package com.carryxyh.client.memcache;

import com.carryxyh.client.AbstractCacheClient;
import com.carryxyh.client.ClientConfig;

/**
 * AbstractMemcacheCacheClient
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public abstract class AbstractMemcacheCacheClient extends AbstractCacheClient implements MemcacheCacheClient {

    protected AbstractMemcacheCacheClient(ClientConfig clientConfig) {
        super(clientConfig);
    }
}
