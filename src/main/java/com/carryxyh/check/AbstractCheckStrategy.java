package com.carryxyh.check;

import com.carryxyh.CacheClient;
import com.carryxyh.CheckResult;
import com.carryxyh.CheckStrategy;
import com.carryxyh.DefaultCheckResult;
import com.carryxyh.constants.CheckStrategys;
import com.carryxyh.constants.ConflictType;

/**
 * AbstractCheckStrategy
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-09
 */
public abstract class AbstractCheckStrategy<C extends CacheClient> implements CheckStrategy<C> {

    protected final C source;

    protected final C target;

    public AbstractCheckStrategy(C source, C target) {
        this.source = source;
        this.target = target;
    }

    protected CheckResult keyCheck(String key) {
        Object s = source.get(key);
        Object t = target.get(key);
        if (s == null && t == null) {
            return DefaultCheckResult.nonConflict();
        } else if (s == null) {
            return DefaultCheckResult.conflict(ConflictType.LACK_SOURCE, CheckStrategys.KEY_EXISTS, null, t);
        } else if (t == null) {
            return DefaultCheckResult.conflict(ConflictType.LACK_TARGET, CheckStrategys.KEY_EXISTS, s, null);
        }
        return DefaultCheckResult.nonConflict();
    }
}
