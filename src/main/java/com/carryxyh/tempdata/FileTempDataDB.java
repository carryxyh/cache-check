package com.carryxyh.tempdata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.carryxyh.TempDataDB;
import com.carryxyh.file.FileOperator;
import com.carryxyh.file.RandomAccessFileOperator;
import com.carryxyh.lifecycle.Endpoint;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * FileTempDataDB
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-28
 */
public class FileTempDataDB extends Endpoint implements TempDataDB {

    private final String path;

    public FileTempDataDB(String path) {
        this.path = path;
    }

    @Override
    public void save(String key, List<ConflictResultData> tempDataList) {
        String holeFilePath = path + key;
        FileOperator fileOperator = new RandomAccessFileOperator(holeFilePath, true);
        List<String> jsonValues = Lists.newArrayList();
        for (ConflictResultData c : tempDataList) {
            jsonValues.add(JSON.toJSONString(c, SerializerFeature.WriteNullListAsEmpty,
                    SerializerFeature.WriteNullStringAsEmpty,
                    SerializerFeature.WriteNullBooleanAsFalse));
        }
        fileOperator.writeData(jsonValues);
    }

    @Override
    public List<ConflictResultData> load(String key) {
        String holeFilePath = path + key;
        FileOperator fileOperator = new RandomAccessFileOperator(holeFilePath, true);
        List<String> jsonValues = fileOperator.loadData();
        List<ConflictResultData> tempDataList = Lists.newArrayList();
        for (String jsonValue : jsonValues) {
            tempDataList.add(JSON.parseObject(jsonValue, ConflictResultData.class));
        }
        return tempDataList;
    }
}
