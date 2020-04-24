package com.carryxyh.config;

/**
 * ClientInfo
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-24
 */
public class ClientInfo {

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
