package com.carryxyh.file;

/**
 * AbstractFileOperator
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-28
 */
public abstract class AbstractFileOperator implements FileOperator {

    protected final String filePath;

    protected static final String LINE_SEPARATOR = System.lineSeparator();

    protected AbstractFileOperator(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String filePath() {
        return filePath;
    }
}
