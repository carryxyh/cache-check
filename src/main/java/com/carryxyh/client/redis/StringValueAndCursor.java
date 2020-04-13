package com.carryxyh.client.redis;

import java.util.List;

/**
 * StringValueAndCursor
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-13
 */
public class StringValueAndCursor extends AbstractValueAndCursor<List<String>> {

    private final List<String> values;

    public StringValueAndCursor(ScanCursor cursor, List<String> values) {
        super(cursor);
        this.values = values;
    }

    @Override
    public List<String> values() {
        return values;
    }
}
