package com.carryxyh.config;

/**
 * AbstractConfig
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-23
 */
public abstract class AbstractConfig implements Config {

    private static final long serialVersionUID = 3382662925636259225L;

    private static final String HOST_PORT = ":";

    private static final String PORT_PWD = "/";

    protected static ClientInfo parseClientInfo(String url) {
        ClientInfo c = new ClientInfo();
        int i = url.indexOf(HOST_PORT);
        if (i == -1) {
            throw new IllegalArgumentException("pls use `host:port/pwd` format.");
        }
        c.host = url.substring(0, i);
        int k = url.indexOf(PORT_PWD);
        if (k == -1) {
            // no password.
            c.port = Integer.parseInt(url.substring(i + 1));
        } else {
            c.port = Integer.parseInt(url.substring(i + 1, k));
            c.password = url.substring(k + 1);
        }
        return c;
    }

    static class ClientInfo {

        String host;

        int port;

        String password;

        @Override
        public String toString() {
            return "ClientInfo{" +
                    "host='" + host + '\'' +
                    ", port=" + port +
                    ", password='" + password + '\'' +
                    '}';
        }
    }
}
