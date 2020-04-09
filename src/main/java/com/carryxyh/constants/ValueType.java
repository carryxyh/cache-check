package com.carryxyh.constants;

/**
 * ValueType
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public enum ValueType {

    NONE(1, "none"),

    MEMCACHE(2, "memcache"),

    STRING(3, "string"),

    LIST(4, "list"),

    SET(5, "set"),

    HASH(6, "hash"),

    ZSET(7, "zset"),

    STREAM(8, "stream"),
    ;

    private int type;

    private String name;

    ValueType(int i, String name) {
        this.type = i;
        this.name = name;
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

    public static ValueType nameOf(String name) {
        for (ValueType v : ValueType.values()) {
            if (v.name.equals(name)) {
                return v;
            }
        }
        return null;
    }
}
