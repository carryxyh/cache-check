package com.carryxyh.constants;

/**
 * CheckStrategys
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public enum CheckStrategys {

    VALUE_EQUALS(1),

    KEY_EXISTS(2),

    VALUE_TYPE(3),
    ;

    private int type;

    CheckStrategys(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static CheckStrategys typeOf(int type) {
        for (CheckStrategys c : CheckStrategys.values()) {
            if (c.type == type) {
                return c;
            }
        }
        return null;
    }
}
