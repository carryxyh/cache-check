package com.carryxyh.constants;

/**
 * ValueType
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public enum ValueType {

    NONE(1, "none"),

    STRING(2, "string"),

    LIST(3, "list"),

    SET(4, "set"),

    HASH(5, "hash"),

    ZSET(6, "zset"),

    STREAM(7, "stream"),

    MEMCACHE(15, "memcache"),
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
