package com.carryxyh.config;

import com.carryxyh.constants.DataInputOutput;

/**
 * InputOutputConfig
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public class InputOutputConfig implements Config {

    private static final long serialVersionUID = 1952941495239637651L;

    private DataInputOutput inputOutput;

    private String inputOutputPath;

    public DataInputOutput getInputOutput() {
        return inputOutput;
    }

    public void setInputOutput(DataInputOutput inputOutput) {
        this.inputOutput = inputOutput;
    }

    public String getInputOutputPath() {
        return inputOutputPath;
    }

    public void setInputOutputPath(String inputOutputPath) {
        this.inputOutputPath = inputOutputPath;
    }
}
