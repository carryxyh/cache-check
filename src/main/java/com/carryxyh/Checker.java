package com.carryxyh;

import com.carryxyh.lifecycle.Lifecycle;

/**
 * Checker
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public interface Checker<SOURCE extends CacheClient, TARGET extends CacheClient> extends Lifecycle {

    SOURCE source();

    TARGET target();

    CheckerConfig config();

    void check(KeysInput input);
}
