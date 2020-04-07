package com.carryxyh.config;

import com.carryxyh.constants.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * AbstractConfig
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public abstract class AbstractConfig implements Config {

    private final Map<String, Object> config = new HashMap<>();

    @Override
    public String getHost() {
        return (String) config.get(Constants.Configs.HOST_KEY);
    }

    @Override
    public int getPort() {
        return (int) config.get(Constants.Configs.PORT_KEY);
    }

    @Override
    public String getPassword() {
        return (String) config.get(Constants.Configs.PASSWORD_KEY);
    }

    @Override
    public Object get(String key) {
        return config.get(key);
    }

    @Override
    public void set(String key, Object value) {
        this.config.put(key, value);
    }
}
