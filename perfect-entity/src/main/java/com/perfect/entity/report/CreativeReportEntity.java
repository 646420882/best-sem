package com.perfect.entity.report;

import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.entity.account.AccountIdEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

/**
 * Created by baizz on 2014-08-07.
 * <p>创意报告</p>
 */
public class CreativeReportEntity extends AccountIdEntity {

    @Id
    private String id;

    @Field(MongoEntityConstants.CREATIVE_ID)
    private Long creativeId;        // 创意ID

    @Field(value = "crtl")
    private String creativeTitle;       //创意标题

    @Field(value = "des1")
    private String description1;        //创意描述1

    @Field(value = "des2")
    private String description2;        //创意描述2

    @Field(value = "url")
    private String showUrl;             //显示url

    @Field(MongoEntityConstants.ADGROUP_ID)
    private Long adgroupId;             //单元ID

    @Field(value = "agna")
    private String adgroupName;         //单元名称

    @Field(MongoEntityConstants.CAMPAIGN_ID)
    private Long campaignId;            //计划ID

    @Field(value = "cpna")
    private String campaignName;        //计划名称

    @Field(value = "pcis")
    private Integer pcImpression;     //PC展现次数

    @Field(value = "pccli")
    private Integer pcClick;      //PC点击次数

    @Field(value = "pcctr")
    private Double pcCtr;     //PC点击率=点击次数/展现次数

    @Field(value = "pccost")
    private BigDecimal pcCost;        //PC消费

    @Field(value = "pccpc")
    private BigDecimal pcCpc;     //PC平均点击价格=消费/点击次数

    @Field(value = "pccpm")
    private BigDecimal pcCpm;       //PC千次展现消费

    @Field(value = "pccs")
    private Double pcConversion;      //PC转化

    @Field(value = "mis")
    private Integer mobileImpression;   //mobile展现次数

    @Field(value = "mcli")
    private Integer mobileClick;   //mobile点击次数

    @Field(value = "mctr")
    private Double mobileCtr;   //mobile点击率=点击次数/展现次数

    @Field(value = "mcost")
    private BigDecimal mobileCost;   //mobile消费

    @Field(value = "mcpc")
    private BigDecimal mobileCpc;   //mobile平均点击价格=消费/点击次数

    @Field(value = "mpt")
    private Double mobilePosition;   //mobile平均排名

    @Field(value = "mcpm")
    private BigDecimal mobileCpm;   //mobile千次展现消费

    @Field(value = "mcs")
    private Double mobileConversion;   //mobile转化

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCreativeId() {
        return creativeId;
    }

    public void setCreativeId(Long creativeId) {
        this.creativeId = creativeId;
    }

    public String getCreativeTitle() {
        return creativeTitle;
    }

    public void setCreativeTitle(String creativeTitle) {
        this.creativeTitle = creativeTitle;
    }

    public String getDescription1() {
        return description1;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public String getShowUrl() {
        return showUrl;
    }

    public void setShowUrl(String showUrl) {
        this.showUrl = showUrl;
    }

    public Long getAdgroupId() {
        return adgroupId;
    }

    public void setAdgroupId(Long adgroupId) {
        this.adgroupId = adgroupId;
    }

    public String getAdgroupName() {
        return adgroupName;
    }

    public void setAdgroupName(String adgroupName) {
        this.adgroupName = adgroupName;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public Integer getPcImpression() {
        return pcImpression;
    }

    public void setPcImpression(Integer pcImpression) {
        this.pcImpression = pcImpression;
    }

    public Integer getPcClick() {
        return pcClick;
    }

    public void setPcClick(Integer pcClick) {
        this.pcClick = pcClick;
    }

    public Double getPcCtr() {
        return pcCtr;
    }

    public void setPcCtr(Double pcCtr) {
        this.pcCtr = pcCtr;
    }

    public BigDecimal getPcCost() {
        return pcCost;
    }

    public void setPcCost(BigDecimal pcCost) {
        this.pcCost = pcCost;
    }

    public BigDecimal getPcCpc() {
        return pcCpc;
    }

    public void setPcCpc(BigDecimal pcCpc) {
        this.pcCpc = pcCpc;
    }

    public BigDecimal getPcCpm() {
        return pcCpm;
    }

    public void setPcCpm(BigDecimal pcCpm) {
        this.pcCpm = pcCpm;
    }

    public Double getPcConversion() {
        return pcConversion;
    }

    public void setPcConversion(Double pcConversion) {
        this.pcConversion = pcConversion;
    }

    public Integer getMobileImpression() {
        return mobileImpression;
    }

    public void setMobileImpression(Integer mobileImpression) {
        this.mobileImpression = mobileImpression;
    }

    public Integer getMobileClick() {
        return mobileClick;
    }

    public void setMobileClick(Integer mobileClick) {
        this.mobileClick = mobileClick;
    }

    public Double getMobileCtr() {
        return mobileCtr;
    }

    public void setMobileCtr(Double mobileCtr) {
        this.mobileCtr = mobileCtr;
    }

    public BigDecimal getMobileCost() {
        return mobileCost;
    }

    public void setMobileCost(BigDecimal mobileCost) {
        this.mobileCost = mobileCost;
    }

    public BigDecimal getMobileCpc() {
        return mobileCpc;
    }

    public void setMobileCpc(BigDecimal mobileCpc) {
        this.mobileCpc = mobileCpc;
    }

    public Double getMobilePosition() {
        return mobilePosition;
    }

    public void setMobilePosition(Double mobilePosition) {
        this.mobilePosition = mobilePosition;
    }

    public BigDecimal getMobileCpm() {
        return mobileCpm;
    }

    public void setMobileCpm(BigDecimal mobileCpm) {
        this.mobileCpm = mobileCpm;
    }

    public Double getMobileConversion() {
        return mobileConversion;
    }

    public void setMobileConversion(Double mobileConversion) {
        this.mobileConversion = mobileConversion;
    }

    @Override
    public String toString() {
        return "CreativeReportEntity{" +
                "id='" + id + '\'' +
                ", creativeId=" + creativeId +
                ", creativeTitle='" + creativeTitle + '\'' +
                ", description1='" + description1 + '\'' +
                ", description2='" + description2 + '\'' +
                ", showUrl='" + showUrl + '\'' +
                ", adgroupId=" + adgroupId +
                ", adgroupName='" + adgroupName + '\'' +
                ", campaignId=" + campaignId +
                ", campaignName='" + campaignName + '\'' +
                ", pcImpression=" + pcImpression +
                ", pcClick=" + pcClick +
                ", pcCtr=" + pcCtr +
                ", pcCost=" + pcCost +
                ", pcCpc=" + pcCpc +
                ", pcCpm=" + pcCpm +
                ", pcConversion=" + pcConversion +
                ", mobileImpression=" + mobileImpression +
                ", mobileClick=" + mobileClick +
                ", mobileCtr=" + mobileCtr +
                ", mobileCost=" + mobileCost +
                ", mobileCpc=" + mobileCpc +
                ", mobilePosition=" + mobilePosition +
                ", mobileCpm=" + mobileCpm +
                ", mobileConversion=" + mobileConversion +
                '}';
    }
}