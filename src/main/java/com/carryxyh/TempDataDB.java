package com.carryxyh;

import com.carryxyh.lifecycle.Lifecycle;
import com.carryxyh.tempdata.TempData;

import java.util.List;

/**
 * TempDataDB
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public interface TempDataDB extends Lifecycle {

    void save(String key, List<TempData> tempDataList);

    List<TempData> load(String key);
}
