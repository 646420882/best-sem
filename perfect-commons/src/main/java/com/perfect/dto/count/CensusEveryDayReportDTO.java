package com.perfect.dto.count;

import java.util.Date;

/**
 * Created by XiaoWei on 2014/11/26.
 */
public class CensusEveryDayReportDTO {
    private String id;
    private String lp;//停留页面
    private Long pvCount;//总浏览量
    private Long uvCount;//总访问客数
    private Long ipCount;//总的IP数
    private Date totalDate;//统计的日期
    public CensusEveryDayReportDTO(String id, String lp, Long pvCount, Long uvCount, Long ipCount, Date totalDate) {
        this.id = id;
        this.lp = lp;
        this.pvCount = pvCount;
        this.uvCount = uvCount;
        this.ipCount = ipCount;
        this.totalDate = totalDate;
    }

    public CensusEveryDayReportDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLp() {
        return lp;
    }

    public void setLp(String lp) {
        this.lp = lp;
    }

    public Long getPvCount() {
        return pvCount;
    }

    public void setPvCount(Long pvCount) {
        this.pvCount = pvCount;
    }

    public Long getUvCount() {
        return uvCount;
    }

    public void setUvCount(Long uvCount) {
        this.uvCount = uvCount;
    }

    public Long getIpCount() {
        return ipCount;
    }

    public void setIpCount(Long ipCount) {
        this.ipCount = ipCount;
    }

    public Date getTotalDate() {
        return totalDate;
    }

    public void setTotalDate(Date totalDate) {
        this.totalDate = totalDate;
    }
}
