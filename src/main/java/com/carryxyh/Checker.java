package com.carryxyh;

import com.carryxyh.lifecycle.Lifecycle;

import java.util.List;

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

    List<TempData> check(KeysInput input);
}
