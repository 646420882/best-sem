package com.perfect.dto.sys;

import com.perfect.dto.BaseDTO;

/**
 * Created by yousheng on 15/12/16.
 */
public class SystemLogDTO extends BaseDTO {


    private String user;

    private String ip;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    private String desc;

    private long time;
}
