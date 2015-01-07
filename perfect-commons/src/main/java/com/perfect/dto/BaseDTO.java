package com.perfect.dto;

import java.io.Serializable;

/**
 * Created by yousheng on 14/12/1.
 */
public class BaseDTO implements Serializable {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
