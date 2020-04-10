package com.carryxyh.tempdata;

import java.io.Serializable;

/**
 * TempData
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public class TempData implements Serializable {

    private static final long serialVersionUID = -4849969938663991415L;

    private String key;

    private Object sourceValue;

    private Object targetValue;

    private int valueType;

    private int conflictType;

    public void setKey(String key) {
        this.key = key;
    }

    public void setSourceValue(Object sourceValue) {
        this.sourceValue = sourceValue;
    }

    public void setTargetValue(Object targetValue) {
        this.targetValue = targetValue;
    }

    public void setValueType(int valueType) {
        this.valueType = valueType;
    }

    public void setConflictType(int conflictType) {
        this.conflictType = conflictType;
    }

    public String getKey() {
        return key;
    }

    public Object getSourceValue() {
        return sourceValue;
    }

    public Object getTargetValue() {
        return targetValue;
    }

    public int getValueType() {
        return valueType;
    }

    public int getConflictType() {
        return conflictType;
    }
}
