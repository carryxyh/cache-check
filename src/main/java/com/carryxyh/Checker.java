package com.carryxyh;

import com.carryxyh.input.KeysInput;
import com.carryxyh.lifecycle.Lifecycle;

/**
 * Checker
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public interface Checker extends Lifecycle {

    CheckerConfig config();

    void check(KeysInput input);
}
