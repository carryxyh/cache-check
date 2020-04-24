package com.carryxyh.input;

import com.carryxyh.KeysInput;

import java.util.List;

/**
 * SystemKeysInput
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-24
 */
public class SystemKeysInput implements KeysInput {

    private final List<String> keys;

    public SystemKeysInput(List<String> keys) {
        this.keys = keys;
    }

    @Override
    public List<String> input() {
        return keys;
    }
}
