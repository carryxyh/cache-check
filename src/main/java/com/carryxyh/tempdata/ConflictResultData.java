package com.carryxyh.tempdata;

import java.io.Serializable;

/**
 * ConflictResultData
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public class ConflictResultData implements Serializable {

    private static final long serialVersionUID = -4849969938663991415L;

    private String key;

    private String fieldOrSubKey;

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

    public String getFieldOrSubKey() {
        return fieldOrSubKey;
    }

    public void setFieldOrSubKey(String fieldOrSubKey) {
        this.fieldOrSubKey = fieldOrSubKey;
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

    @Override
    public String toString() {
        return "{" +
                "key='" + key + '\'' +
                ", fieldOrSubKey='" + fieldOrSubKey + '\'' +
                ", sourceValue=" + sourceValue +
                ", targetValue=" + targetValue +
                ", valueType=" + valueType +
                ", conflictType=" + conflictType +
                '}';
    }
}
