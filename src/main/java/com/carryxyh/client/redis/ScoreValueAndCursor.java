package com.carryxyh.client.redis;

import javafx.util.Pair;

import java.util.List;

/**
 * ScoreValueAndCursor
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-13
 */
public class ScoreValueAndCursor extends AbstractValueAndCursor<List<Pair<String, Double>>> {

    private final List<Pair<String, Double>> values;

    public ScoreValueAndCursor(ScanCursor scanCursor, List<Pair<String, Double>> values) {
        super(scanCursor);
        this.values = values;
    }

    @Override
    public List<Pair<String, Double>> values() {
        return values;
    }
}
