package com.carryxyh.check.redis;

import com.carryxyh.CheckResult;
import com.carryxyh.constants.ConflictType;

import java.util.List;

/**
 * RedisComplicitCheckResult
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-15
 */
public class RedisComplicitCheckResult implements CheckResult {

    private boolean conflict = false;

    private List<CheckResult> results;

    protected RedisComplicitCheckResult() {
    }

    protected RedisComplicitCheckResult(boolean conflict, List<CheckResult> checkResults) {
        this.conflict = conflict;
        this.results = checkResults;
    }

    public static RedisComplicitCheckResult nonConflict() {
        return new RedisComplicitCheckResult();
    }

    public static RedisComplicitCheckResult conflict(List<CheckResult> checkResults) {
        return new RedisComplicitCheckResult(true, checkResults);
    }

    public void setConflict(boolean conflict) {
        this.conflict = conflict;
    }

    public void setResults(List<CheckResult> results) {
        this.results = results;
    }

    @Override
    public boolean isConflict() {
        return conflict;
    }

    @Override
    public ConflictType getConflictType() {
        return null;
    }

    @Override
    public String subKey() {
        return null;
    }

    @Override
    public List<CheckResult> sourceValue() {
        return this.results;
    }

    @Override
    public List<CheckResult> targetValue() {
        return sourceValue();
    }
}
