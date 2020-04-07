package com.carryxyh;

import com.carryxyh.client.CacheClient;
import com.carryxyh.config.Config;
import com.carryxyh.temp.TempDataDB;

/**
 * CheckerConfig
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public interface CheckerConfig extends Config {

    TempDataDB buildTempDataDB();

    CacheClient buildSource();

    CacheClient buildTarget();
}
