package com.perfect.dto;

/**
 * Created by baizz on 2014-11-26.
 */
public class UrlDTO extends BaseDTO {

    private String request;

    private boolean idle;

    private long finishTime;

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    public boolean isIdle() {
        return idle;
    }

    public void setIdle(boolean idle) {
        this.idle = idle;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

}
