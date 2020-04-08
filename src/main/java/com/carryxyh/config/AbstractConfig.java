package com.carryxyh.config;

import com.carryxyh.Config;

import java.util.HashMap;
import java.util.Map;

/**
 * AbstractConfig
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public abstract class AbstractConfig implements Config {

    protected final Map<String, Object> config = new HashMap<>();

    @Override
    public Object get(String key) {
        return config.get(key);
    }

    @Override
    public void set(String key, Object value) {
        this.config.put(key, value);
    }
}
