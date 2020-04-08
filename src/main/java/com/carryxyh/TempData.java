package com.carryxyh;

import com.carryxyh.common.Result;

import java.io.Serializable;

/**
 * TempData
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public class TempData implements Serializable {

    private String key;

    private Object sourceValue;

    private Object targetValue;

    private int valueType;

    private int conflictType;


}
