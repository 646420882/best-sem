package com.perfect.vo;


/**
 * Created by XiaoWei on 2014/11/12.
 */
public class CensusVO {
    private String censusUrl;//统计访问量的Url地址
    private int totalCount;//总访问量(这个是不以独立ip，独立cookie为标准，访问一次，则该数据记录一次)
    private int totalPv;//等价于totalCount
    private int totalUv;//以独立uuid为标识区分，一个uuid为一个Uv访问量
    private int totalIp;//以独立ip为标识区分,一个ip为一个Ip访问量
    private double avgTime;//平均访问时间
    private double alert;//跳出率
    private int convert;//转化

    public CensusVO() {
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPv() {
        return totalPv;
    }

    public void setTotalPv(int totalPv) {
        this.totalPv = totalPv;
    }

    public int getTotalUv() {
        return totalUv;
    }

    public void setTotalUv(int totalUv) {
        this.totalUv = totalUv;
    }

    public int getTotalIp() {
        return totalIp;
    }

    public void setTotalIp(int totalIp) {
        this.totalIp = totalIp;
    }

    public double getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(double avgTime) {
        this.avgTime = avgTime;
    }

    public double getAlert() {
        return alert;
    }

    public void setAlert(double alert) {
        this.alert = alert;
    }

    public int getConvert() {
        return convert;
    }

    public void setConvert(int convert) {
        this.convert = convert;
    }

    public String getCensusUrl() {
        return censusUrl;
    }

    public void setCensusUrl(String censusUrl) {
        this.censusUrl = censusUrl;
    }

    @Override
    public String toString() {
        return "ConstantsDTO{" +
                "censusUrl='" + censusUrl + '\'' +
                ", totalCount=" + totalCount +
                ", totalPv=" + totalPv +
                ", totalUv=" + totalUv +
                ", totalIp=" + totalIp +
                ", avgTime=" + avgTime +
                ", alert=" + alert +
                ", convert=" + convert +
                '}';
    }

    public enum CensusStatus {
        TO_DAY, LAST_DAY, LAST_WEEK, LAST_MONTH;
    }
}
