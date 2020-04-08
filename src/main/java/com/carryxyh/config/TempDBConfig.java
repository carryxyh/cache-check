package com.carryxyh.config;

import com.carryxyh.constants.TempDataDBType;

/**
 * TempDBConfig
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public class TempDBConfig implements Config {

    private TempDataDBType tempDataDBType;

    private String tempDataDBHost;

    private int tempDataDBPort;

    private String tempDataDBPassword;

    private int tempDataDBTimeout = 5000;

    private String tempDBPath = "./";
}
