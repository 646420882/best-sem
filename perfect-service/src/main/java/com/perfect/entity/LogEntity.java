package com.perfect.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Deprecated
@Document(collection = "logs")
public class LogEntity {

    private String id;

    private String accountId;

    private String op;

    private long datetime;

    private int status;

    private Object param;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }
}
