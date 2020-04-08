package com.carryxyh.lifecycle;

import com.carryxyh.config.Config;

/**
 * Lifecycle
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public interface Lifecycle {

    void init(Config config) throws Exception;

    void start() throws Exception;

    void stop() throws Exception;

    void close() throws Exception;
}
