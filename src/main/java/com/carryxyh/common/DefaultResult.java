package com.carryxyh.common;

/**
 * DefaultResult
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public class DefaultResult implements Result {

    private final Object result;

    public DefaultResult(Object result) {
        this.result = result;
    }

    @Override
    public Object result() {
        return result;
    }

    public static Result valueOf(Object result) {
        return new DefaultResult(result);
    }
}
