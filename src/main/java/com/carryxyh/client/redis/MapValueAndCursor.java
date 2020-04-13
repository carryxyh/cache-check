package com.carryxyh.client.redis;

import java.util.List;
import java.util.Map;

/**
 * MapValueAndCursor
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-13
 */
public class MapValueAndCursor extends AbstractValueAndCursor<Map<String, String>> {

    private final Map<String, String> values;

    public MapValueAndCursor(ScanCursor scanCursor, Map<String, String> values) {
        super(scanCursor);
        this.values = values;
    }

    @Override
    public Map<String, String> values() {
        return values;
    }
}
