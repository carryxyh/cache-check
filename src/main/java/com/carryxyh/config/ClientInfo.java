package com.carryxyh.config;

import java.io.Serializable;

/**
 * ClientInfo
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-24
 */
public class ClientInfo implements Serializable {

    private static final long serialVersionUID = 8567662801609115330L;

    private final String host;

    private final int port;

    public ClientInfo(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "ClientInfo{" +
                "host='" + host + '\'' +
                ", port=" + port +
                '}';
    }
}
