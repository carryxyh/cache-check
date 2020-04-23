package com.carryxyh.config;

import com.carryxyh.constants.CacheClient;
import com.carryxyh.constants.CacheClusterMode;
import com.carryxyh.constants.CacheType;

/**
 * ClientConfig
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public class ClientConfig extends AbstractConfig {

    private static final long serialVersionUID = 7567463800092612659L;

    private CacheType cacheType;

    private CacheClient cacheClient;

    private CacheClusterMode cacheClusterMode;

    private String host;

    private int port;

    private String password;

    private int timeout = 5000;

    public CacheType getCacheType() {
        return cacheType;
    }

    public void setCacheType(CacheType cacheType) {
        this.cacheType = cacheType;
    }

    public CacheClient getCacheClient() {
        return cacheClient;
    }

    public void setCacheClient(CacheClient cacheClient) {
        this.cacheClient = cacheClient;
    }

    public CacheClusterMode getCacheClusterMode() {
        return cacheClusterMode;
    }

    public void setCacheClusterMode(CacheClusterMode cacheClusterMode) {
        this.cacheClusterMode = cacheClusterMode;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
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
        if (url == null) {
            return;
        }
        ClientInfo c = parseClientInfo(url);
        this.host = c.host;
        this.port = c.port;
        this.password = c.password;
    }
}
