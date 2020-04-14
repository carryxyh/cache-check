package com.carryxyh;

import com.carryxyh.lifecycle.Lifecycle;
import com.carryxyh.tempdata.ConflictResultData;

import java.util.List;

/**
 * TempDataDB
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public interface TempDataDB extends Lifecycle {

    void save(String key, List<ConflictResultData> tempDataList);

    List<ConflictResultData> load(String key);
}
