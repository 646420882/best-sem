package com.perfect.param;

/**
 * Created by yousheng on 15/12/16.
 */
public class SystemLogParams {

    private Long start;

    private Long end;

    public Long getStart() {
        return start;
    }

    public SystemLogParams setStart(Long start) {
        this.start = start;
        return this;
    }

    public Long getEnd() {
        return end;
    }

    public SystemLogParams setEnd(Long end) {
        this.end = end;
        return this;
    }

    public String getUser() {
        return user;
    }

    public SystemLogParams setUser(String user) {
        this.user = user;
        return this;
    }

    private String user;
}
