package com.carryxyh.constants;

import org.apache.commons.lang3.StringUtils;

/**
 * DataInputOutput
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public enum DataInputOutput {

    HOLE_CHECK(1),

    SYSTEM(2),

    FILE(3),
    ;

    private int input;

    DataInputOutput(int input) {
        this.input = input;
    }

    public int getInput() {
        return input;
    }

    public static DataInputOutput inputOf(int input) {
        for (DataInputOutput d : DataInputOutput.values()) {
            if (d.input == input) {
                return d;
            }
        }
        return null;
    }

    public static DataInputOutput nameOf(String type) {
        for (DataInputOutput d : DataInputOutput.values()) {
            if (StringUtils.equalsIgnoreCase(d.name(), type)) {
                return d;
            }
        }
        return null;
    }
}
