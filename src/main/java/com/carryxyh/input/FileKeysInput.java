package com.carryxyh.input;

import com.carryxyh.KeysInput;
import com.carryxyh.file.FileOperator;
import com.carryxyh.file.RandomAccessFileOperator;

import java.util.List;

/**
 * FileKeysInput
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-28
 */
public class FileKeysInput implements KeysInput {

    private final FileOperator fileOperator;

    public FileKeysInput(String inputPath) {
        this.fileOperator = new RandomAccessFileOperator(inputPath, false);
    }

    @Override
    public List<String> input() {
        return fileOperator.loadData();
    }
}
