package com.perfect.dto;

/**
 * Created by baizz on 2014-11-26.
 */
public class CookieDTO extends BaseDTO {

    private String cookie;

    private boolean idle;

    private long finishTime;

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public boolean isIdle() {
        return idle;
    }

    public void setIdle(boolean idle) {
        this.idle = idle;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }
}
