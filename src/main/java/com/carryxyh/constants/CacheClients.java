package com.carryxyh.constants;

import org.apache.commons.lang3.StringUtils;

/**
 * CacheClients
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public enum CacheClients {

    LETTUCE(1),

    XMEMCACHE(2),
    ;

    private int client;

    CacheClients(int client) {
        this.client = client;
    }

    public int getClient() {
        return client;
    }

    public static CacheClients clientOf(int client) {
        for (CacheClients c : CacheClients.values()) {
            if (c.client == client) {
                return c;
            }
        }
        return null;
    }

    public static CacheClients nameOf(String type) {
        for (CacheClients c : CacheClients.values()) {
            if (StringUtils.equalsIgnoreCase(c.name(), type)) {
                return c;
            }
        }
        return null;
    }
}
