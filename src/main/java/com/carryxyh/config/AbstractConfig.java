package com.carryxyh.config;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * AbstractConfig
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-23
 */
public abstract class AbstractConfig implements Config {

    private static final long serialVersionUID = 3382662925636259225L;

    private static final String SPLIT = ",";

    private static final String HOST_PORT = ":";

    private static final String PORT_PWD = "/";

    protected static List<ClientInfo> parseClientInfo(String url) {
        List<ClientInfo> clientInfos = Lists.newArrayList();
        if (StringUtils.isBlank(url)) {
            return clientInfos;
        }
        String[] split = url.split(SPLIT);
        for (String s : split) {
            int i = s.indexOf(HOST_PORT);
            if (i == -1) {
                throw new IllegalArgumentException("pls use `host:port` format.");
            }
            String host = s.substring(0, i);
            int k = s.indexOf(PORT_PWD);
            int port;
            if (k == -1) {
                // no password.
                port = Integer.parseInt(s.substring(i + 1));
            } else {
                port = Integer.parseInt(s.substring(i + 1, k));
            }
            ClientInfo c = new ClientInfo(host, port);
            clientInfos.add(c);
        }
        return clientInfos;
    }
}
