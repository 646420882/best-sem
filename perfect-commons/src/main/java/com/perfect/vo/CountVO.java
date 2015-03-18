package com.perfect.vo;

/**
 * Created by XiaoWei on 2014/12/5.
 */
public class CountVO {
    private String lp;
    private String uid;
    private String ip;
    private int count;

    public String getLp() {
        return lp;
    }

    public void setLp(String lp) {
        this.lp = lp;
    }

    public int getCount() {
        return count;
    }


    public void setCount(int count) {
        this.count = count;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "CountVO{" +
                "lp='" + lp + '\'' +
                ", uid='" + uid + '\'' +
                ", ip='" + ip + '\'' +
                ", count=" + count +
                '}';
    }
}
