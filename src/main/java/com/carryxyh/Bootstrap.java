package com.carryxyh;

import com.carryxyh.config.CheckerConfig;
import com.carryxyh.config.ClientConfig;
import com.carryxyh.config.InputOutputConfig;
import com.carryxyh.config.TempDBConfig;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

/**
 * Hello world!
 */
public class Bootstrap {

    public static void main(String[] args) throws Exception {

        Options options = new Options();

        options.addOption("h", "help", false, "Print help.");

        // required part options. -------------------------------------------------------------------------------------

        options.addOption("ct",
                "cachetype",
                true,
                "cache type, support redis and memcache.");

        options.addOption("cm", "cachemode", true,
                "cache mode, support standalone, cluster and sentinel(for redis only).");

        options.addOption("i", "inputtype", true,
                "input type, support redis_hole_check, system and file.");

        options.addOption("o", "outtype", true,
                "out type, support system and file.");

        options.addOption("s", "source", true,
                "source cache, use host:port/password format.");

        options.addOption("t", "target", true,
                "target cache, use host:port/password format.");

        // non-required part options. ---------------------------------------------------------------------------------

        /*----------------- client config -----------------*/

        options.addOption("cc", true,
                "cache client, support lettuce(for redis) and xmemcached(for memcache).");

        options.addOption("ctm", true,
                "client operate timeout, 5000 for default");

        /*----------------- checker config -----------------*/

        options.addOption("cs", true,
                "check strategy, suport value_type, key_exists and value_type(for redis only).");

        options.addOption("kr", true,
                "checker rounds, should >= 1, 3 for default.");

        options.addOption("kp", true,
                "checker parallel, should >= 1, 10 for default.");

        options.addOption("ki", true,
                "checker internal, 60 * 1000(60 seconds) for default.");

        options.addOption("cpt", true,
                "complicate threshold, 256 for default.");

        options.addOption("cpb", true,
                "complicate batch compareSize, 30 for default.");

        /*----------------- input config -----------------*/

        options.addOption("ip", true,
                "input path. when input type is file, this will be needed.");

        options.addOption("ik", true,
                "input key. when input type is system, this will be needed.");

        /*----------------- input config -----------------*/

        options.addOption("op", true,
                "output path. when output type is file, this will be needed.");

        /*----------------- temp config -----------------*/

        options.addOption("tpt", true,
                "temp db type. support memory(default), file, mysql and redis.");

        options.addOption("tpu", true,
                "temp db url, use host:port/password format.");

        options.addOption("tptm", true,
                "temp db operate timeout, 5000 for default.");

        options.addOption("tpp", true,
                "temp db path, when temp db type is file, this will be needed.");

        // parse. -----------------------------------------------------------------------------------------------------

        HelpFormatter hf = new HelpFormatter();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        if (cmd.hasOption("h")) {
            hf.printHelp("cache-check", options, true);
        }

        CheckerConfig checkerConfig = new CheckerConfig();
        ClientConfig sourceConfig = new ClientConfig();
        ClientConfig targetConfig = new ClientConfig();
        InputOutputConfig inputConfig = new InputOutputConfig();
        InputOutputConfig outputConfig = new InputOutputConfig();
        TempDBConfig tempDBConfig = new TempDBConfig();
    }
}
