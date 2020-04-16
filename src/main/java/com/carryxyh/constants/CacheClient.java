package com.carryxyh.constants;

import org.apache.commons.lang3.StringUtils;

/**
 * CacheClient
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public enum CacheClient {

    LETTUCE(1),

    XMEMCACHE(2),
    ;

    private int client;

    CacheClient(int client) {
        this.client = client;
    }

    public int getClient() {
        return client;
    }

    public static CacheClient clientOf(int client) {
        for (CacheClient c : CacheClient.values()) {
            if (c.client == client) {
                return c;
            }
        }
        return null;
    }

    public static CacheClient nameOf(String type) {
        for (CacheClient c : CacheClient.values()) {
            if (StringUtils.equalsIgnoreCase(c.name(), type)) {
                return c;
            }
        }
        return null;
    }
}
