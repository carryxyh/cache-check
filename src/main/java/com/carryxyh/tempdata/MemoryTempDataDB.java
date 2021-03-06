package com.carryxyh.tempdata;

import com.carryxyh.TempDataDB;
import com.carryxyh.lifecycle.Endpoint;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * MemoryTempDataDB
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public class MemoryTempDataDB extends Endpoint implements TempDataDB {

    private final Map<String, List<ConflictResultData>> DB = Maps.newHashMap();

    @Override
    public void save(String key, List<ConflictResultData> tempDataList) {
        DB.put(key, tempDataList);
    }

    @Override
    public List<ConflictResultData> load(String key) {
        return DB.get(key);
    }
}
