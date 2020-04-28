package com.carryxyh;

import com.carryxyh.config.CheckerConfig;
import com.carryxyh.config.ClientConfig;
import com.carryxyh.config.InputOutputConfig;
import com.carryxyh.config.TempDBConfig;
import com.carryxyh.tempdata.ConflictResultData;

import java.util.List;

/**
 * Runner
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-23
 */
public final class Runner implements Runnable {

    private final CheckerConfig checkerConfig;

    private final ClientConfig source;

    private final ClientConfig target;

    private final InputOutputConfig input;

    private final InputOutputConfig output;

    private final TempDBConfig tempDB;

    public Runner(CheckerConfig checkerConfig,
                  ClientConfig source,
                  ClientConfig target,
                  InputOutputConfig input,
                  InputOutputConfig output,
                  TempDBConfig tempDB) {
        this.checkerConfig = checkerConfig;
        this.source = source;
        this.target = target;
        this.input = input;
        this.output = output;
        this.tempDB = tempDB;
    }

    @Override
    public void run() {

        try {

            // build source. ------------------------------------------------------------------------------------------

            CacheClient s = source.buildCacheClient();

            // build target. ------------------------------------------------------------------------------------------

            CacheClient t = target.buildCacheClient();

            // build temp db. -----------------------------------------------------------------------------------------

            TempDataDB tempDataDB = tempDB.buildTempDataDB();

            // build checker. -----------------------------------------------------------------------------------------

            Checker<?> checker = checkerConfig.buildChecker(source.getCacheType(),
                    source.getCacheClient(),
                    tempDataDB,
                    s,
                    t);

            // build input. -------------------------------------------------------------------------------------------

            KeysInput keysInput = input.buildInput(s);

            // build output. ------------------------------------------------------------------------------------------

            ConflictOutput conflictOutput = output.buildOutput();

            // check. -------------------------------------------------------------------------------------------------

            List<ConflictResultData> checkResults = checker.check(keysInput);
            conflictOutput.output(checkResults);

        } catch (Exception e) {
            throw new IllegalStateException("error occurs while checking...", e);
        }
    }
}
