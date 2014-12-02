package com.perfect.dto.account;

import java.math.BigDecimal;

/**
 * Created by SubDong on 2014/11/27.
 */
public class CreativeReportDTO extends AccountIdDTO{

    private Long creativeId;

    private String creativeTitle;

    private String description1;

    private String description2;

    private String showUrl;

    private Long adgroupId;

    private String adgroupName;

    private Long campaignId;

    private String campaignName;

    private Integer pcImpression;     //PC展现次数

    private Integer pcClick;      //PC点击次数

    private Double pcCtr;     //PC点击率=点击次数/展现次数

    private BigDecimal pcCost;        //PC消费

    private BigDecimal pcCpc;     //PC平均点击价格=消费/点击次数

    private BigDecimal pcCpm;       //PC千次展现消费

    private Double pcConversion;      //PC转化

    private Integer mobileImpression;

    private Integer mobileClick;

    private Double mobileCtr;

    private BigDecimal mobileCost;

    private BigDecimal mobileCpc;

    private Double mobilePosition;

    private BigDecimal mobileCpm;

    private Double mobileConversion;

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
