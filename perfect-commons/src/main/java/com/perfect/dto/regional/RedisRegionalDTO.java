package com.perfect.dto.regional;

/**
 * Created by SubDong on 2014/9/29.
 */
public class RedisRegionalDTO implements Comparable<RedisRegionalDTO>{

    //地域id
    private String regionalId;
    //地域名称
    private String regionalName;

    private Integer shots;

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

    public Integer getShots() {
        return shots;
    }

    public void setShots(Integer shots) {
        this.shots = shots;
    }

    @Override
    public int compareTo(RedisRegionalDTO o) {
        return this.getShots().compareTo(o.getShots());
    }
}
