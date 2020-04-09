package com.carryxyh;

import com.carryxyh.config.CheckerConfig;
import com.carryxyh.config.ClientConfig;
import com.carryxyh.config.InputOutputConfig;
import com.carryxyh.config.TempDBConfig;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

/**
 * Hello world!
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Options options = new Options();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        if (cmd.hasOption("t")) {
            String t = cmd.getOptionValue("t");
        }

        CheckerConfig checkerConfig = new CheckerConfig();
        ClientConfig sourceConfig = new ClientConfig();
        ClientConfig targetConfig = new ClientConfig();
        InputOutputConfig inputConfig = new InputOutputConfig();
        InputOutputConfig outputConfig = new InputOutputConfig();
        TempDBConfig tempDBConfig = new TempDBConfig();
    }
}
