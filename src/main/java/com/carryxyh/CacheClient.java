package com.carryxyh;

import com.carryxyh.common.Command;
import com.carryxyh.common.Result;
import com.carryxyh.lifecycle.Lifecycle;

/**
 * CacheClient
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public interface CacheClient extends Lifecycle {

    Result get(Command getCmd);
}
