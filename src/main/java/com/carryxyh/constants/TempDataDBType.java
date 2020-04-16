package com.carryxyh.constants;

import org.apache.commons.lang3.StringUtils;

/**
 * TempDataDBType
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public enum TempDataDBType {

    MEMORY(1),

    FILE(2),

    MYSQL(3),

    REDIS(4),

    MEMCACHE(5),
    ;

    private int type;

    TempDataDBType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static TempDataDBType typeOf(int type) {
        for (TempDataDBType t : TempDataDBType.values()) {
            if (t.type == type) {
                return t;
            }
        }
        return null;
    }

    public static TempDataDBType nameOf(String type) {
        for (TempDataDBType t : TempDataDBType.values()) {
            if (StringUtils.equalsIgnoreCase(t.name(), type)) {
                return t;
            }
        }
        return null;
    }
}
