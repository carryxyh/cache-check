package com.carryxyh;

import com.carryxyh.lifecycle.Lifecycle;

/**
 * CacheClients
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public interface CacheClient extends Lifecycle {

    Object get(String key);
}
