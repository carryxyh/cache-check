package com.carryxyh.output;

import com.carryxyh.ConflictOutput;
import com.carryxyh.lifecycle.Endpoint;
import com.carryxyh.tempdata.ConflictResultData;

import java.util.List;

/**
 * SystemConflictOutput
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-16
 */
public class SystemConflictOutput extends Endpoint implements ConflictOutput {

    @Override
    public void output(List<ConflictResultData> conflicts) {
        for (ConflictResultData c : conflicts) {
            System.out.println(c);
        }
    }
}
