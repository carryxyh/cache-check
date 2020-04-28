package com.carryxyh.config;

import com.carryxyh.CacheClient;
import com.carryxyh.client.memcache.xmemcache.XMemcacheClient;
import com.carryxyh.client.redis.lettuce.LettuceClusterClient;
import com.carryxyh.client.redis.lettuce.LettuceStandaloneClient;
import com.carryxyh.constants.CacheClients;
import com.carryxyh.constants.CacheClusterMode;
import com.carryxyh.constants.CacheType;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * ClientConfig
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public class ClientConfig extends AbstractConfig {

    private static final long serialVersionUID = 7567463800092612659L;

    private CacheType cacheType;

    private CacheClients cacheClient;

    private CacheClusterMode cacheClusterMode;

    private List<ClientInfo> clientInfos;

    private String password;

    private int timeout = 5000;

    public CacheType getCacheType() {
        return cacheType;
    }

    public void setCacheType(CacheType cacheType) {
        this.cacheType = cacheType;
    }

    public CacheClients getCacheClient() {
        return cacheClient;
    }

    public void setCacheClient(CacheClients cacheClient) {
        this.cacheClient = cacheClient;
    }

    public CacheClusterMode getCacheClusterMode() {
        return cacheClusterMode;
    }

    public void setCacheClusterMode(CacheClusterMode cacheClusterMode) {
        this.cacheClusterMode = cacheClusterMode;
    }

    public List<ClientInfo> getClientInfos() {
        return clientInfos;
    }

    public void setClientInfos(List<ClientInfo> clientInfos) {
        this.clientInfos = clientInfos;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setUrl(String url) {
        if (StringUtils.isBlank(url)) {
            return;
        }
        this.clientInfos = parseClientInfo(url);
    }

    public CacheClient buildCacheClient() {
        CacheType cacheType = getCacheType();
        CacheClients cacheClient = getCacheClient();
        if (cacheType == CacheType.REDIS) {
            if (cacheClient == CacheClients.LETTUCE) {
                CacheClusterMode cacheClusterMode = getCacheClusterMode();
                if (cacheClusterMode == CacheClusterMode.SENTINEL || cacheClusterMode == CacheClusterMode.STANDALONE) {
                    CacheClient source = new LettuceStandaloneClient();
                    source.init(this);
                    return source;
                } else if (cacheClusterMode == CacheClusterMode.CLUSTER) {
                    CacheClient source = new LettuceClusterClient();
                    source.init(this);
                    return source;
                } else {
                    throw new IllegalArgumentException(
                            "can't match cache cluster mode for : " + cacheClusterMode.name());
                }
            } else {
                throw new IllegalArgumentException("can't match cache client for redis : " + cacheClient.name());
            }
        } else if (cacheType == CacheType.MEMCACHE) {
            if (cacheClient == CacheClients.XMEMCACHE) {
                CacheClient source = new XMemcacheClient();
                source.init(this);
                return source;
            } else {
                throw new IllegalArgumentException("can't match cache client for memcache : " + cacheClient.name());
            }
        } else {
            throw new IllegalArgumentException("can't match cache type for : " + cacheType.name());
        }
    }
}
