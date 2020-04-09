package com.carryxyh.check.redis;

import com.carryxyh.TempData;
import com.carryxyh.TempDataDB;
import com.carryxyh.check.AbstractChecker;
import com.carryxyh.client.redis.lettuce.LettuceClient;

import java.util.List;

/**
 * LettuceChecker
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-09
 */
public final class LettuceChecker extends AbstractChecker<LettuceClient, LettuceClient> {

    public LettuceChecker(TempDataDB tempDataDB,
                          LettuceClient source,
                          LettuceClient target) {
        super(tempDataDB, source, target);
    }

    @Override
    protected List<TempData> firstCheck(List<String> keys) {
        return null;
    }

    @Override
    protected List<TempData> roundCheck(List<TempData> tempData) {
        return null;
    }
}
