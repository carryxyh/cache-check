package com.carryxyh.common;

import java.io.Serializable;

/**
 * Result
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public interface Result<T> extends Serializable {

    T result();
}
