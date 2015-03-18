package com.perfect.dto.count;

import com.perfect.dto.BaseDTO;

import java.util.Date;

/**
 * Created by XiaoWei on 2014/11/26.
 */
public class CensusEveryDayReportDTO extends BaseDTO {
    private String lp;//停留页面
    private Long pvCount;//总浏览量
    private Long uvCount;//总访问客数
    private Long ipCount;//总的IP数
    private Date totalDate;//统计的日期

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
