package com.carryxyh.constants;

import org.apache.commons.lang3.StringUtils;

/**
 * DataInputOutputs
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public enum DataInputOutputs {

    HOLE_CHECK(1),

    SYSTEM(2),

    FILE(3),
    ;

    private int input;

    DataInputOutputs(int input) {
        this.input = input;
    }

    public int getInput() {
        return input;
    }

    public static DataInputOutputs inputOf(int input) {
        for (DataInputOutputs d : DataInputOutputs.values()) {
            if (d.input == input) {
                return d;
            }
        }
        return null;
    }

    public static DataInputOutputs nameOf(String type) {
        for (DataInputOutputs d : DataInputOutputs.values()) {
            if (StringUtils.equalsIgnoreCase(d.name(), type)) {
                return d;
            }
        }
        return null;
    }
}
