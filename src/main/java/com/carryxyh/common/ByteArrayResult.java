package com.carryxyh.common;

/**
 * ByteArrayResult
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-12
 */
public class ByteArrayResult implements Result<byte[]> {

    private static final long serialVersionUID = -2989553210151211673L;

    private final byte[] result;

    public ByteArrayResult(byte[] result) {
        this.result = result;
    }

    @Override
    public byte[] result() {
        return result;
    }

    public static ByteArrayResult valueOf(byte[] result) {
        return new ByteArrayResult(result);
    }
}
