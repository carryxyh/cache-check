package com.carryxyh.common;

import java.util.List;

/**
 * DefaultCommand
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public class DefaultCommand implements Command {

    private final String key;

    private final List<Object> value;

    public DefaultCommand(String key, List<Object> value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String key() {
        return null;
    }

    @Override
    public List<Object> params() {
        return null;
    }
}
