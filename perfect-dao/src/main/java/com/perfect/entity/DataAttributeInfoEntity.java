package com.perfect.entity;

/**
 * Created by baizz on 2014-7-2.
 */
public class DataAttributeInfoEntity {

    private String name;

    private Object before;

    private Object after;

    public DataAttributeInfoEntity() {
    }

    public DataAttributeInfoEntity(String name, Object before, Object after) {
        this.name = name;
        this.before = before;
        this.after = after;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getBefore() {
        return before;
    }

    public void setBefore(Object before) {
        this.before = before;
    }

    public Object getAfter() {
        return after;
    }

    public void setAfter(Object after) {
        this.after = after;
    }
}
