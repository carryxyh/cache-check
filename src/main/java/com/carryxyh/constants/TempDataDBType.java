package com.carryxyh.constants;

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
}
