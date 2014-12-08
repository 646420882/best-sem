package com.perfect.vo;

/**
 * Created by SubDong on 2014/12/5.
 * 存放分组从数据库查出的依据数据
 */
public class BasedDataVO{
    //分组url
    private String tp;
    //分组搜索引擎
    private String se;
    //分组uv
    private String uid;
    //url在数据库出现的次数
    private int count;

    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    public String getSe() {
        return se;
    }

    public void setSe(String se) {
        this.se = se;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "BasedDataVO{" +
                "tp='" + tp + '\'' +
                ", se='" + se + '\'' +
                ", uid='" + uid + '\'' +
                ", count=" + count +
                '}';
    }
}
