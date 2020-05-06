package com.carryxyh;

import com.carryxyh.config.CheckerConfig;
import com.carryxyh.config.ClientConfig;
import com.carryxyh.config.InputOutputConfig;
import com.carryxyh.config.TempDBConfig;
import com.carryxyh.lifecycle.Lifecycle;
import com.carryxyh.tempdata.ConflictResultData;
import com.google.common.collect.Lists;

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
        List<Lifecycle> lifecycles = Lists.newArrayList();
        try {
            // build instance.
            CacheClient s = source.buildCacheClient();
            lifecycles.add(s);

            CacheClient t = target.buildCacheClient();
            lifecycles.add(t);

            TempDataDB tempDataDB = tempDB.buildTempDataDB();
            lifecycles.add(tempDataDB);

            Checker<?> checker = checkerConfig.buildChecker(source.getCacheType(),
                    source.getCacheClient(),
                    tempDataDB,
                    s,
                    t);
            lifecycles.add(checker);

            KeysInput keysInput = input.buildInput(s);
            ConflictOutput conflictOutput = output.buildOutput();
            lifecycles.add(conflictOutput);

            // check and output.
            List<ConflictResultData> checkResults = checker.check(keysInput);
            conflictOutput.output(checkResults);

        } finally {
            for (Lifecycle l : lifecycles) {
                if (l != null) {
                    l.close();
                }
            }
        }
    }
}
