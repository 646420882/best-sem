package com.perfect.core;

/**
 * Created by yousheng on 15/12/16.
 */
public class SystemUserInfo {
    private String ip;

    private String user;

    public String getIp() {
        return ip;
    }

    public SystemUserInfo setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getUser() {
        return user;
    }

    public SystemUserInfo setUser(String user) {
        this.user = user;
        return this;
    }

    @Override
    public String toString() {
        return "SystemUserInfo{" +
                "ip='" + ip + '\'' +
                ", user='" + user + '\'' +
                '}';
    }
}
