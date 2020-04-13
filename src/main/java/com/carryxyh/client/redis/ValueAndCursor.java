package com.carryxyh.client.redis;

/**
 * ValueAndCursor
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-13
 */
public interface ValueAndCursor<V> {

    ScanCursor cursor();

    V values();
}
