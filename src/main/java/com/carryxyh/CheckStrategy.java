package com.carryxyh;

/**
 * CheckStrategy
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public interface CheckStrategy {

    boolean check(String key, Object sourceValue, Object targetValue);
}