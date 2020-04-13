package com.carryxyh.client.redis;

/**
 * DefaultScanArgs
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-13
 */
public class DefaultScanArgs implements ScanArgs {

    private final long count;

    private final String match;

    public DefaultScanArgs(long count, String match) {
        this.count = count;
        this.match = match;
    }

    @Override
    public long count() {
        return count;
    }

    @Override
    public String match() {
        return match;
    }
}
