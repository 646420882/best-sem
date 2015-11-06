package com.perfect.entity;

import com.perfect.commons.constants.MongoEntityConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * Created by john on 2014/11/17.
 */
@Deprecated //慧眼相关代码，已经被遗弃
@Document(collection = MongoEntityConstants.SYS_CENSUS_EVERYDAY_REPORT)
public class CensusEveryDayReportEntity {

    @Id
    private String id;

    @Field(value = "lp")
    private String lp;//停留页面

    @Field(value = "pvs")
    private Long pvCount;//总浏览量

    @Field(value = "uvs")
    private Long uvCount;//总访问客数

    @Field(value = "ips")
    private Long ipCount;//总的IP数

    private Date totalDate;//统计的日期


    public CensusEveryDayReportEntity(String id, String lp, Long pvCount, Long uvCount, Long ipCount, Date totalDate) {
        this.id = id;
        this.lp = lp;
        this.pvCount = pvCount;
        this.uvCount = uvCount;
        this.ipCount = ipCount;
        this.totalDate = totalDate;
    }

    public CensusEveryDayReportEntity() {
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
