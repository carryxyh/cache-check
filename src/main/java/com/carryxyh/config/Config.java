package com.carryxyh.config;

/**
 * Config
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public interface Config {

    String getHost();

    int getPort();

    String getPassword();

    Object get(String key);

    void set(String key, Object value);
}
