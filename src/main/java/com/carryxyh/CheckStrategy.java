package com.carryxyh;

/**
 * CheckStrategy
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public interface CheckStrategy<C extends CacheClient> {

    CheckResult check(String key, String subKey);
}
