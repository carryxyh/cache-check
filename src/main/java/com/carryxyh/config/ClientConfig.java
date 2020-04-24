package com.carryxyh.config;

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
}
