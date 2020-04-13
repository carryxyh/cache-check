package com.carryxyh.client.redis;

/**
 * AbstractValueAndCursor
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-13
 */
public abstract class AbstractValueAndCursor<V> implements ValueAndCursor<V> {

    private final ScanCursor scanCursor;

    public AbstractValueAndCursor(ScanCursor scanCursor) {
        this.scanCursor = scanCursor;
    }

    @Override
    public ScanCursor cursor() {
        return scanCursor;
    }
}
