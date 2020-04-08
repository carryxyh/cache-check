package com.carryxyh.client;

import com.carryxyh.Config;

/**
 * ClientConfig
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public interface ClientConfig extends Config {

    String getHost();

    int getPort();

    String getPassword();
}
