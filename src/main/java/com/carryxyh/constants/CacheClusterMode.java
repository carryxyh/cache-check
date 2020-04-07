package com.carryxyh.constants;

/**
 * CacheClusterMode
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public enum CacheClusterMode {

    STANDALONE,

    CLUSTER,

    /**
     * only for redis
     */
    SENTINEL;
}
