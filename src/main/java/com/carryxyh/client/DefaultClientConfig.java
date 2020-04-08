package com.carryxyh.client;

import com.carryxyh.config.AbstractConfig;
import com.carryxyh.constants.Constants;

/**
 * DefaultClientConfig
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public class DefaultClientConfig extends AbstractConfig implements ClientConfig {

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
}
