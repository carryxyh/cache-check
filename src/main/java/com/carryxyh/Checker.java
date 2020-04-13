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
public interface Checker<C extends CacheClient> extends Lifecycle {

    C source();

    C target();

    List<TempData> check(KeysInput input);
}
