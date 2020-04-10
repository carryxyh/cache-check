package com.carryxyh.common;

import com.google.common.collect.Maps;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * DefaultCommand
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public class DefaultCommand implements Command {

    private final String key;

    private final List<Object> value;

    private final Map<String, Object> attachments = Maps.newHashMap();

    private DefaultCommand(String key, List<Object> value) {
        this.key = key;
        this.value = value;
    }

    public static DefaultCommand nonValueCmd(String key) {
        return new DefaultCommand(key, null);
    }

    public static DefaultCommand valueCmd(String key, Object... values) {
        return new DefaultCommand(key, Arrays.asList(values));
    }

    @Override
    public String key() {
        return null;
    }

    @Override
    public List<Object> params() {
        return null;
    }

    @Override
    public Object removeAttachment(String key) {
        return attachments.remove(key);
    }

    @Override
    public void putAttachment(String key, Object obj) {
        this.attachments.put(key, obj);
    }

    @Override
    public Object getAttachment(String key) {
        return attachments.get(key);
    }
}
