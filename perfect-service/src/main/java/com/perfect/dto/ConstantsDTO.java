package com.perfect.dto;


/**
 * Created by XiaoWei on 2014/11/12.
 */
public class ConstantsDTO {
    private Integer totalPv;
    private Integer totalUv;
    private Integer ipCount;
    private Integer avgTime;

    public Integer getTotalPv() {
        return totalPv;
    }

    public void setTotalPv(Integer totalPv) {
        this.totalPv = totalPv;
    }

    public Integer getTotalUv() {
        return totalUv;
    }

    public void setTotalUv(Integer totalUv) {
        this.totalUv = totalUv;
    }

    public Integer getIpCount() {
        return ipCount;
    }

    public void setIpCount(Integer ipCount) {
        this.ipCount = ipCount;
    }

    public Integer getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(Integer avgTime) {
        this.avgTime = avgTime;
    }

    public Integer getConvert() {
        return convert;
    }

    public void setConvert(Integer convert) {
        this.convert = convert;
    }

    private Integer convert;
}
