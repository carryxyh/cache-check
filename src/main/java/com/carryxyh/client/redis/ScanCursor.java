package com.carryxyh.client.redis;

/**
 * ScanCursor
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-13
 */
public interface ScanCursor {

    String current();

    boolean isFinished();
}
