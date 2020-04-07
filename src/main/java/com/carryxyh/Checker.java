package com.carryxyh;

import com.carryxyh.config.Config;
import com.carryxyh.input.KeysInput;
import com.carryxyh.lifecycle.Lifecycle;

/**
 * Checker
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public interface Checker extends Lifecycle {

    Config config();

    <T> void check(KeysInput<T> input);
}
