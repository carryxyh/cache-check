package com.carryxyh.file;

import com.google.common.collect.Lists;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * RandomAccessFileOperator
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-28
 */
public class RandomAccessFileOperator extends AbstractFileOperator {

    private final RandomAccessFile randomAccessFile;

    public RandomAccessFileOperator(String filePath, boolean output) {
        super(filePath);
        File f = new File(filePath);
        if (output) {
            try {
                if (f.exists()) {
                    boolean r = f.delete();
                    if (!r) {
                        throw new IllegalStateException("can't delete old result file.");
                    }
                } else {
                    if (filePath.endsWith(File.pathSeparator)) {
                        throw new IllegalStateException("should be a file.");
                    }
                    File parentFile = f.getParentFile();
                    while (parentFile != null && !parentFile.exists()) {
                        parentFile.mkdir();
                    }
                    f.createNewFile();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            if (!f.exists()) {
                throw new IllegalArgumentException("input file is not exists.");
            }
        }
        try {
            randomAccessFile = new RandomAccessFile(f, "rw");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void appendData(String data) {
        try {
            randomAccessFile.write((data + LINE_SEPARATOR).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeData(List<String> data) {
        StringBuilder builder = new StringBuilder(512);
        for (String s : data) {
            builder.append(s).append(LINE_SEPARATOR);
        }
        try {
            randomAccessFile.write((builder + LINE_SEPARATOR).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> loadData() {
        List<String> data = Lists.newArrayList();
        try {
            String s = randomAccessFile.readLine();
            while (s != null) {
                data.add(s);
                s = randomAccessFile.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data;
    }
}
