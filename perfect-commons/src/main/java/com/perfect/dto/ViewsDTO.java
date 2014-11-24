package com.perfect.dto;


/**
 * Created by john on 2014/11/17.
 * 浏览量
 */
public class ViewsDTO {

    private String field;

    private long count;


    public ViewsDTO() {
    }

    public ViewsDTO(String field, long count) {
        this.field = field;
        this.count = count;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}

