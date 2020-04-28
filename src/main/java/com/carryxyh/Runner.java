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
        CacheClient s = null;
        CacheClient t = null;
        TempDataDB tempDataDB = null;
        Checker<?> checker = null;
        ConflictOutput conflictOutput = null;
        try {
            // build instance.
            s = source.buildCacheClient();
            t = target.buildCacheClient();
            tempDataDB = tempDB.buildTempDataDB();
            checker = checkerConfig.buildChecker(source.getCacheType(),
                    source.getCacheClient(),
                    tempDataDB,
                    s,
                    t);
            KeysInput keysInput = input.buildInput(s);
            conflictOutput = output.buildOutput();

            // check and output.
            List<ConflictResultData> checkResults = checker.check(keysInput);
            conflictOutput.output(checkResults);

        } finally {
            if (s != null) {
                s.close();
            }
            if (t != null) {
                t.close();
            }
            if (tempDataDB != null) {
                tempDataDB.close();
            }
            if (checker != null) {
                checker.close();
            }
            if (conflictOutput != null) {
                conflictOutput.close();
            }
        }
    }
}
