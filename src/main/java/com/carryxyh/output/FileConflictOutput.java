package com.carryxyh.output;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.carryxyh.ConflictOutput;
import com.carryxyh.file.FileOperator;
import com.carryxyh.file.RandomAccessFileOperator;
import com.carryxyh.lifecycle.Endpoint;
import com.carryxyh.tempdata.ConflictResultData;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * FileConflictOutput
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-28
 */
public class FileConflictOutput extends Endpoint implements ConflictOutput {

    private final FileOperator fileOperator;

    public FileConflictOutput(String file) {
        this.fileOperator = new RandomAccessFileOperator(file, true);
    }

    @Override
    public void output(List<ConflictResultData> conflicts) {
        List<String> jsonValues = Lists.newArrayList();
        for (ConflictResultData c : conflicts) {
            jsonValues.add(JSON.toJSONString(c, SerializerFeature.WriteMapNullValue,
                    SerializerFeature.WriteNullListAsEmpty,
                    SerializerFeature.WriteNullBooleanAsFalse));
        }
        try {
            fileOperator.writeData(jsonValues);
        } finally {
            fileOperator.closeFile();
        }
    }
}
