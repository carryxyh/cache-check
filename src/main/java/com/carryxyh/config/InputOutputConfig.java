package com.carryxyh.config;

import com.carryxyh.constants.DataInputOutputs;
import com.google.common.collect.Lists;
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
}
