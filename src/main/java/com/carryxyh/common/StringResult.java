package com.carryxyh.common;

/**
 * StringResult
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-10
 */
public final class StringResult implements Result<String> {

    private static final long serialVersionUID = 788754431962017227L;

    private final String result;

    public StringResult(String result) {
        this.result = result;
    }

    @Override
    public String result() {
        return result;
    }
}
