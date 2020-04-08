package com.carryxyh;

/**
 * Config
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public interface Config {

    Object get(String key);

    void set(String key, Object value);

    Checker buildChecker();
}
