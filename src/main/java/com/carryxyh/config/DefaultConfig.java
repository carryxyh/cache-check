package com.carryxyh.config;

/**
 * DefaultConfig
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public final class DefaultConfig implements Config {

    private ClientConfig sourceConfig = new ClientConfig();

    private ClientConfig targetConfig = new ClientConfig();

    private TempDBConfig tempDBConfig = new TempDBConfig();

    private InputOutputConfig inputConfig = new InputOutputConfig();

    private InputOutputConfig outputConfig = new InputOutputConfig();

    public ClientConfig getSourceConfig() {
        return sourceConfig;
    }

    public ClientConfig getTargetConfig() {
        return targetConfig;
    }

    public TempDBConfig getTempDBConfig() {
        return tempDBConfig;
    }

    public InputOutputConfig getInputConfig() {
        return inputConfig;
    }

    public InputOutputConfig getOutputConfig() {
        return outputConfig;
    }
}
