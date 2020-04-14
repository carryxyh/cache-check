package com.carryxyh.constants;

/**
 * ConflictType
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public enum ConflictType {

    LACK_SOURCE(1),

    LACK_TARGET(2),

    VALUE(3),

    VALUE_TYPE(4),

    FIELD_VALUE(5),
    ;

    private int type;

    ConflictType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static ConflictType typeOf(int type) {
        for (ConflictType c : ConflictType.values()) {
            if (c.type == type) {
                return c;
            }
        }
        return null;
    }
}
