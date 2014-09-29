package com.perfect.dto;

/**
 * Created by SubDong on 2014/9/29.
 */
public class RedisRegionalDTO {

    //地域id
    private String regionalId;
    //地域名称
    private String regionalName;

    public String getRegionalId() {
        return regionalId;
    }

    public void setRegionalId(String regionalId) {
        this.regionalId = regionalId;
    }

    public String getRegionalName() {
        return regionalName;
    }

    public void setRegionalName(String regionalName) {
        this.regionalName = regionalName;
    }

    @Override
    public String toString() {
        return "RedisRegionalDTO{" +
                "regionalId='" + regionalId + '\'' +
                ", regionalName='" + regionalName + '\'' +
                '}';
    }


}
