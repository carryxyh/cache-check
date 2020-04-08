package com.carryxyh.constants;

/**
 * ValueType
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public enum ValueType {

    MEMCACHE(1),

    STRING(2),

    LIST(3),

    SET(4),

    HASH(5),

    ZSET(6),
    ;

    private int type;

    ValueType(int i) {
        this.type = i;
    }

    public int getType() {
        return type;
    }

    public static ValueType typeOf(int type) {
        for (ValueType v : ValueType.values()) {
            if (v.type == type) {
                return v;
            }
        }
        return null;
    }
}
