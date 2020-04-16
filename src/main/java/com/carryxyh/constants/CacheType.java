package com.carryxyh.constants;

import org.apache.commons.lang3.StringUtils;

/**
 * CacheType
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public enum CacheType {

    REDIS(1),

    MEMCACHE(2),
    ;

    private int type;

    CacheType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static CacheType typeOf(int type) {
        for (CacheType c : CacheType.values()) {
            if (c.type == type) {
                return c;
            }
        }
        return null;
    }

    public static CacheType nameOf(String type) {
        for (CacheType c : CacheType.values()) {
            if (StringUtils.equalsIgnoreCase(c.name(), type)) {
                return c;
            }
        }
        return null;
    }
}
