package com.carryxyh;

import com.carryxyh.config.CheckerConfig;
import com.carryxyh.config.ClientConfig;
import com.carryxyh.config.InputOutputConfig;
import com.carryxyh.config.TempDBConfig;
import com.carryxyh.constants.CacheClient;
import com.carryxyh.constants.CacheClusterMode;
import com.carryxyh.constants.CacheType;
import com.carryxyh.constants.CheckStrategys;
import com.carryxyh.constants.DataInputOutput;
import com.carryxyh.constants.TempDataDBType;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

/**
 * Hello world!
 */
public class Bootstrap {

    private static final String CONFIG_H = "h";

    private static final String CONFIG_CT = "ct";

    private static final String CONFIG_CM = "cm";

    private static final String CONFIG_I = "i";

    private static final String CONFIG_O = "o";

    private static final String CONFIG_S = "s";

    private static final String CONFIG_T = "t";

    private static final String CONFIG_CC = "cc";

    private static final String CONFIG_CTM = "ctm";

    private static final String CONFIG_CS = "cs";

    private static final String CONFIG_KR = "kr";

    private static final String CONFIG_KP = "kp";

    private static final String CONFIG_KI = "ki";

    private static final String CONFIG_CPT = "cpt";

    private static final String CONFIG_CPB = "cpb";

    private static final String CONFIG_IP = "ip";

    private static final String CONFIG_IK = "ik";

    private static final String CONFIG_OP = "op";

    private static final String CONFIG_TPT = "tpt";

    private static final String CONFIG_TPU = "tpu";

    private static final String CONFIG_TPTM = "tptm";

    private static final String CONFIG_TPP = "tpp";

    public static void main(String[] args) throws Exception {

        Options options = new Options();

        options.addOption(CONFIG_H, "help", false, "Print help.");

        // required part options. -------------------------------------------------------------------------------------

        options.addOption(CONFIG_CT,
                "cachetype",
                true,
                "cache type, required, support redis and memcache.");

        options.addOption(CONFIG_CM, "cachemode", true,
                "cache mode, required, support standalone, cluster and sentinel(for redis only).");

        options.addOption(CONFIG_I, "inputtype", true,
                "input type, required, support redis_hole_check, system and file.");

        options.addOption(CONFIG_O, "outtype", true,
                "out type, required, support system and file.");

        options.addOption(CONFIG_S, "source", true,
                "source cache, required, use host:port/password format.");

        options.addOption(CONFIG_T, "target", true,
                "target cache, required, use host:port/password format.");

        // non-required part options. ---------------------------------------------------------------------------------

        /*----------------- client config -----------------*/

        options.addOption(CONFIG_CC, true,
                "cache client, support lettuce(for redis) and xmemcached(for memcache).");

        options.addOption(CONFIG_CTM, true,
                "client operate timeout, 5000 for default");

        /*----------------- checker config -----------------*/

        options.addOption(CONFIG_CS, true,
                "check strategy, suport value_type, key_exists and value_type(for redis only).");

        options.addOption(CONFIG_KR, true,
                "checker rounds, should >= 1, 3 for default.");

        options.addOption(CONFIG_KP, true,
                "checker parallel, should >= 1, 10 for default.");

        options.addOption(CONFIG_KI, true,
                "checker internal, 60 * 1000(60 seconds) for default.");

        options.addOption(CONFIG_CPT, true,
                "complicate threshold, 256 for default.");

        options.addOption(CONFIG_CPB, true,
                "complicate batch compareSize, 30 for default.");

        /*----------------- input config -----------------*/

        options.addOption(CONFIG_IP, true,
                "input path. when input type is file, this will be needed.");

        options.addOption(CONFIG_IK, true,
                "input key. when input type is system or cache type is memcache, this will be needed.");

        /*----------------- input config -----------------*/

        options.addOption(CONFIG_OP, true,
                "output path. when output type is file, this will be needed.");

        /*----------------- temp config -----------------*/

        options.addOption(CONFIG_TPT, true,
                "temp db type. support memory(default), file, mysql and redis.");

        options.addOption(CONFIG_TPU, true,
                "temp db url, use host:port/password format.");

        options.addOption(CONFIG_TPTM, true,
                "temp db operate timeout, 5000 for default.");

        options.addOption(CONFIG_TPP, true,
                "temp db path, when temp db type is file, this will be needed.");

        // parse. -----------------------------------------------------------------------------------------------------

        HelpFormatter hf = new HelpFormatter();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        if (cmd.hasOption(CONFIG_H)) {
            hf.printHelp("cache-check", options, true);
        }

        String inputKey;
        CacheType cacheType;
        if (cmd.hasOption(CONFIG_CT)) {
            String config = cmd.getOptionValue(CONFIG_CT);
            cacheType = CacheType.nameOf(config);
            if (cacheType == null) {
                throw new IllegalArgumentException("can't find matched cache type for : " + config);
            }
        } else {
            throw new IllegalArgumentException("must config `" + CONFIG_CT + "` for cache type.");
        }

        CacheClusterMode cacheMode;
        if (cmd.hasOption(CONFIG_CM)) {
            String config = cmd.getOptionValue(CONFIG_CM);
            cacheMode = CacheClusterMode.nameOf(config);
            if (cacheMode == null) {
                throw new IllegalArgumentException("can't find matched cache mode for : " + config);
            }
        } else {
            throw new IllegalArgumentException("must config `" + CONFIG_CM + "` for cache mode.");
        }

        String inputPath;
        DataInputOutput inputType;
        if (cmd.hasOption(CONFIG_I)) {
            String config = cmd.getOptionValue(CONFIG_I);
            inputType = DataInputOutput.nameOf(config);
            if (inputType == null) {
                throw new IllegalArgumentException("can't find matched input type for : " + config);
            }

            if (cacheType == CacheType.MEMCACHE && inputType == DataInputOutput.HOLE_CHECK) {
                throw new IllegalArgumentException(
                        "input type should not be hole_check since memcache doesn't support cmd to iterate all keys.");
            }

            if (inputType == DataInputOutput.FILE) {
                if (cmd.hasOption(CONFIG_IP)) {
                    inputPath = cmd.getOptionValue(CONFIG_IP);
                } else {
                    throw new IllegalArgumentException("`" + CONFIG_IP + "`" +
                            " is required when use input type is file.");
                }
            }
        } else {
            throw new IllegalArgumentException("must config `" + CONFIG_I + "` for input type.");
        }

        String outputPath;
        DataInputOutput outputType;
        if (cmd.hasOption(CONFIG_O)) {
            String config = cmd.getOptionValue(CONFIG_O);
            outputType = DataInputOutput.nameOf(config);
            if (outputType == null) {
                throw new IllegalArgumentException("can't find matched output type for : " + config);
            }

            if (outputType == DataInputOutput.FILE) {
                if (cmd.hasOption(CONFIG_OP)) {
                    outputPath = cmd.getOptionValue(CONFIG_OP);
                } else {
                    throw new IllegalArgumentException("`" + CONFIG_OP + "`" +
                            " is required when use output type is file.");
                }
            }
        } else {
            throw new IllegalArgumentException("must config `" + CONFIG_O + "` for output type.");
        }

        String source;
        if (cmd.hasOption(CONFIG_S)) {
            source = cmd.getOptionValue(CONFIG_S);
        } else {
            throw new IllegalArgumentException("must config `" + CONFIG_S + "` for source.");
        }

        String target;
        if (cmd.hasOption(CONFIG_T)) {
            source = cmd.getOptionValue(CONFIG_T);
        } else {
            throw new IllegalArgumentException("must config `" + CONFIG_T + "` for target.");
        }

        CacheClient cacheClient;
        if (cmd.hasOption(CONFIG_CC)) {
            String config = cmd.getOptionValue(CONFIG_CC);
            cacheClient = CacheClient.nameOf(config);
            if (cacheClient == null) {
                throw new IllegalArgumentException("can't find matched cache client for : " + config);
            }
        } else {
            if (CacheType.REDIS == cacheType) {
                cacheClient = CacheClient.LETTUCE;
            } else if (CacheType.MEMCACHE == cacheType) {
                cacheClient = CacheClient.XMEMCACHE;
            } else {
                throw new IllegalArgumentException("illegal cache type!");
            }
        }

        // cache operate timeout.
        int cacheOperateTimeout = Integer.parseInt(cmd.getOptionValue(CONFIG_CTM, "5000"));

        // checker configs. -------------------------------------------------------------------------------------------
        CheckStrategys checkStrategys = CheckStrategys.nameOf(
                cmd.getOptionValue(CONFIG_CS, CheckStrategys.VALUE_EQUALS.name()));
        if (checkStrategys == null) {
            throw new IllegalArgumentException("can't match check strategy for : " + cmd.getOptionValue(CONFIG_CS));
        }
        int checkerRounds = Integer.parseInt(cmd.getOptionValue(CONFIG_KR, "3"));
        int checkerParallel = Integer.parseInt(cmd.getOptionValue(CONFIG_KP, "10"));
        long checkInternal = Long.parseLong(cmd.getOptionValue(CONFIG_KI, "60000"));
        int complicateThreshold = Integer.parseInt(cmd.getOptionValue(CONFIG_CPT, "256"));
        int complicateBatchCompareSize = Integer.parseInt(cmd.getOptionValue(CONFIG_CPB, "30"));

        CheckerConfig checkerConfig = new CheckerConfig();
        checkerConfig.setCheckStrategys(checkStrategys);
        checkerConfig.setRounds(checkerRounds);
        checkerConfig.setParallel(checkerParallel);
        checkerConfig.setInternal(checkInternal);
        checkerConfig.setComplicateThreshold(complicateThreshold);
        checkerConfig.setComplicateBatchCompareSize(complicateBatchCompareSize);

        // temp db configs. -------------------------------------------------------------------------------------------
        String tempDBUrl;
        String tempDBPath;
        int tempDBOperateTimeout;
        TempDataDBType tempDataDBType = TempDataDBType.nameOf(
                cmd.getOptionValue(CONFIG_TPT, TempDataDBType.MEMORY.name()));
        if (tempDataDBType == null) {
            throw new IllegalArgumentException("can't match temp data db type for : " + cmd.getOptionValue(CONFIG_TPT));
        } else {
            if (tempDataDBType == TempDataDBType.MYSQL || tempDataDBType == TempDataDBType.REDIS) {
                if (cmd.hasOption(CONFIG_TPU)) {
                    tempDBUrl = cmd.getOptionValue(CONFIG_TPU);
                } else {
                    throw new IllegalArgumentException("`" + CONFIG_TPU + "`" +
                            " is required when temp db type is mysql and redis.");
                }
                tempDBOperateTimeout = Integer.parseInt(cmd.getOptionValue(CONFIG_TPTM, "5000"));
            } else if (tempDataDBType == TempDataDBType.FILE) {
                tempDBPath = cmd.getOptionValue(CONFIG_TPP, "./");
            }
        }

        TempDBConfig tempDBConfig = new TempDBConfig();
        tempDBConfig.setTempDataDBType(tempDataDBType);


        ClientConfig sourceConfig = new ClientConfig();
        ClientConfig targetConfig = new ClientConfig();
        InputOutputConfig inputConfig = new InputOutputConfig();
        InputOutputConfig outputConfig = new InputOutputConfig();

    }
}
