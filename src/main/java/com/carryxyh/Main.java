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

        // required part options. -------------------------------------------------------------------------------------

        // {@link CacheType} enum.
        options.addRequiredOption("ct",
                "cachetype",
                true,
                "cache type, support redis and memcache.");

        // {@link CacheClusterMode} enum
        options.addRequiredOption("cm", "cachemode", true,
                "cache mode, support standalone, cluster and sentinel(for redis only).");

        // {@link DataInputOutput} enum.
        options.addRequiredOption("i", "inputtype", true,
                "input type, support redis_hole_check, system and file.");

        // {@link DataInputOutput} enum.
        options.addRequiredOption("o", "outtype", true,
                "out type, support system and file.");

        options.addRequiredOption("s", "source", true,
                "source cache, use host:port/password format.");

        options.addRequiredOption("t", "target", true,
                "target cache, use host:port/password format.");

        // non-required part options. ---------------------------------------------------------------------------------

        // {@link CacheClient} enum.
        options.addOption("cc", true,
                "cache client, support lettuce(for redis) and xmemcached(for memcache).");

        options.addOption("cs", true,
                "check strategy, suport value_type, key_exists and value_type(for redis only).");

        options.addOption("cr", true,
                "check rounds, should >= 1, 3 for default.");

        options.addOption("cp", true,
                "check parallel, should >= 1, 10 for default.");

        options.addOption("cp", true,
                "check parallel, should >= 1, 10 for default.");

        // parse. -----------------------------------------------------------------------------------------------------

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
