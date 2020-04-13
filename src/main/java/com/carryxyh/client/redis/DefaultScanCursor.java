package com.carryxyh.client.redis;

/**
 * DefaultScanCursor
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-13
 */
public class DefaultScanCursor implements ScanCursor {

    private final String cursor;

    private final boolean isFinished;

    public DefaultScanCursor(String cursor, boolean isFinished) {
        this.cursor = cursor;
        this.isFinished = isFinished;
    }

    @Override
    public String current() {
        return cursor;
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
