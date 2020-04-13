package com.carryxyh.client.redis;

/**
 * ScanArgs
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-13
 */
public interface ScanArgs {

    long count();

    String match();
}
