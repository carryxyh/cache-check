package com.carryxyh.config;

import com.carryxyh.CacheClient;
import com.carryxyh.ConflictOutput;
import com.carryxyh.KeysInput;
import com.carryxyh.client.redis.RedisCacheClient;
import com.carryxyh.constants.DataInputOutputs;
import com.carryxyh.input.RedisHoleKeysInput;
import com.carryxyh.input.SystemKeysInput;
import com.carryxyh.output.SystemConflictOutput;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * InputOutputConfig
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public class InputOutputConfig extends AbstractConfig {

    private static final long serialVersionUID = 1952941495239637651L;

    private DataInputOutputs inputOutput;

    private String inputOutputPath;

    private List<String> inputKeys;

    public List<String> getInputKeys() {
        return inputKeys;
    }

    public void setInputKeys(List<String> inputKeys) {
        this.inputKeys = inputKeys;
    }

    public void setInputKeys(String inputKeys) {
        if (StringUtils.isBlank(inputKeys)) {
            return;
        }
        String[] split = inputKeys.split(",");
        this.inputKeys = Lists.newArrayList(split);
    }

    public DataInputOutputs getInputOutput() {
        return inputOutput;
    }

    public void setInputOutput(DataInputOutputs inputOutput) {
        this.inputOutput = inputOutput;
    }

    public String getInputOutputPath() {
        return inputOutputPath;
    }

    public void setInputOutputPath(String inputOutputPath) {
        this.inputOutputPath = inputOutputPath;
    }

    public ConflictOutput buildOutput() {
        DataInputOutputs inputOutput = getInputOutput();
        if (inputOutput == DataInputOutputs.SYSTEM) {
            return new SystemConflictOutput();
        } else if (inputOutput == DataInputOutputs.FILE) {
            // TODO
            return null;
        } else {
            throw new IllegalArgumentException("can't match input type for : " + inputOutput.name());
        }
    }

    public KeysInput buildInput(CacheClient source) {
        DataInputOutputs inputOutput = getInputOutput();
        if (inputOutput == DataInputOutputs.SYSTEM) {
            List<String> inputKeys = getInputKeys();
            if (CollectionUtils.isEmpty(inputKeys)) {
                throw new IllegalArgumentException("empty input keys.");
            }
            return new SystemKeysInput(inputKeys);
        } else if (inputOutput == DataInputOutputs.FILE) {
            // TODO
            return null;
        } else if (inputOutput == DataInputOutputs.HOLE_CHECK) {
            return new RedisHoleKeysInput((RedisCacheClient) source);
        } else {
            throw new IllegalArgumentException("can't match input type for : " + inputOutput.name());
        }
    }
}
