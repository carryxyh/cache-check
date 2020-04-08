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

    Object removeAttachment(String key);

    void putAttachment(String key, Object obj);

    Object getAttachment(String key);
}
