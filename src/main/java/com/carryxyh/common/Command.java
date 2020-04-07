package com.carryxyh.common;

import java.util.List;

/**
 * Command
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public interface Command {

    String key();

    List<Object> params();
}
