package com.carryxyh.constants;

import org.apache.commons.lang3.StringUtils;

/**
 * CacheClusterMode
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public enum CacheClusterMode {

    STANDALONE(1),

    CLUSTER(2),

    /**
     * only for redis
     */
    SENTINEL(3);

    private int mode;

    CacheClusterMode(int mode) {
        this.mode = mode;
    }

    public int getMode() {
        return mode;
    }

    public static CacheClusterMode modeOf(int mode) {
        for (CacheClusterMode c : CacheClusterMode.values()) {
            if (c.mode == mode) {
                return c;
            }
        }
        return null;
    }

    public static CacheClusterMode nameOf(String type) {
        for (CacheClusterMode c : CacheClusterMode.values()) {
            if (StringUtils.equalsIgnoreCase(c.name(), type)) {
                return c;
            }
        }
        return null;
    }
}
