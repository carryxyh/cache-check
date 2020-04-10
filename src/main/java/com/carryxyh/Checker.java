package com.carryxyh;

import com.carryxyh.lifecycle.Lifecycle;
import com.carryxyh.tempdata.TempData;

import java.util.List;

/**
 * Checker
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public interface Checker<S extends CacheClient, T extends CacheClient> extends Lifecycle {

    S source();

    T target();

    List<TempData> check(KeysInput input);
}
