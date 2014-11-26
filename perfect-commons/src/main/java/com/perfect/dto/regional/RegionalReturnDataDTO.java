package com.perfect.dto.regional;

/**
 * Created by SubDong on 2014/9/29.
 * 存放拼装好的返回数据，处理过
 */
public class RegionalReturnDataDTO implements Comparable<RegionalReturnDataDTO>{

    //地域id
    private String regionalId;
    //地域名称
    private String regionalName;
    //排序字段
    //因为 regionalId有可能是多个地域ID拼装的数据所以不能直接用regionalId来排序
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
    public int compareTo(RegionalReturnDataDTO o) {
        return this.getShots().compareTo(o.getShots());
    }
}
