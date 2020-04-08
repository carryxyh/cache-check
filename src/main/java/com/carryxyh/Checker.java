package com.carryxyh;

import com.carryxyh.lifecycle.Lifecycle;

/**
 * Checker
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public interface Checker extends Lifecycle {

    CacheClient source();

    CacheClient target();

    CheckerConfig config();

    void check(KeysInput input);
}
