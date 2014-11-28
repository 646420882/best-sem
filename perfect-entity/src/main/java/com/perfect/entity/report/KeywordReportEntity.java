package com.perfect.entity.report;

import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.entity.account.AccountIdEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

/**
 * Created by baizz on 2014-08-06.
 */
public class KeywordReportEntity extends AccountIdEntity implements Comparable<KeywordReportEntity> {

    @Id
    private String id;

    @Field(MongoEntityConstants.KEYWORD_ID)
    private Long keywordId;

    @Field(value = "kwna")
    private String keywordName;

    @Field(MongoEntityConstants.ADGROUP_ID)
    private Long adgroupId;

    @Field(value = "agna")
    private String adgroupName;

    @Field(MongoEntityConstants.CAMPAIGN_ID)
    private Long campaignId;

    @Field(value = "cpna")
    private String campaignName;

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

    @Field(value = "pcpt")
    private Double pcPosition;       //PC平均排名

    @Field(value = "mis")
    private Integer mobileImpression;

    @Field(value = "mcli")
    private Integer mobileClick;

    @Field(value = "mctr")
    private Double mobileCtr;

    @Field(value = "mcost")
    private BigDecimal mobileCost;

    @Field(value = "mcpc")
    private BigDecimal mobileCpc;

    @Field(value = "mcpm")
    private BigDecimal mobileCpm;

    @Field(value = "mcs")
    private Double mobileConversion;

    @Field(value = "mpt")
    private Double mobilePosition;
    private Integer quality = 0;
    private Integer matchType;

    private String orderBy;//排序而已

    public Integer getMatchType() {
        return matchType;
    }

    public void setMatchType(Integer matchType) {
        this.matchType = matchType;
    }

    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(Long keywordId) {
        this.keywordId = keywordId;
    }

    public String getKeywordName() {
        return keywordName;
    }

    public void setKeywordName(String keywordName) {
        this.keywordName = keywordName;
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

    public Double getPcPosition() {
        return pcPosition;
    }

    public void setPcPosition(Double pcPosition) {
        this.pcPosition = pcPosition;
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

    public Double getMobilePosition() {
        return mobilePosition;
    }

    public void setMobilePosition(Double mobilePosition) {
        this.mobilePosition = mobilePosition;
    }

    @Override
    public String toString() {
        return "KeywordReportEntity{" +
                "id='" + id + '\'' +
                ", keywordId=" + keywordId +
                ", keywordName='" + keywordName + '\'' +
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
                ", pcPosition=" + pcPosition +
                ", mobileImpression=" + mobileImpression +
                ", mobileClick=" + mobileClick +
                ", mobileCtr=" + mobileCtr +
                ", mobileCost=" + mobileCost +
                ", mobileCpc=" + mobileCpc +
                ", mobileCpm=" + mobileCpm +
                ", mobileConversion=" + mobileConversion +
                ", mobilePosition=" + mobilePosition +
                '}';
    }

    @Override
    public int compareTo(KeywordReportEntity o) {
        switch (o.getOrderBy()) {
            case "1":
                return this.getPcImpression().compareTo(o.getPcImpression());
            case "2":
                return this.getPcClick().compareTo(o.getPcClick());
            case "-2":
                return o.getPcClick().compareTo(this.getPcClick());
            case "3":
                return this.getPcCost().compareTo(o.getPcCost());
            case "-3":
                return o.getPcCost().compareTo(this.getPcCost());
            case "4":
                return this.getPcCpc().compareTo(o.getPcCpc());
            case "-4":
                return o.getPcCpc().compareTo(this.getPcCpc());
            case "5":
                return this.getPcCtr().compareTo(o.getPcCtr());
            case "-5":
                return o.getPcCtr().compareTo(this.getPcCtr());
            case "6":
                return this.getPcConversion().compareTo(o.getPcConversion());
            case "-6":
                return o.getPcConversion().compareTo(this.getPcConversion());
            case "7":
                return this.getPcPosition().compareTo(o.getPcPosition());
            case "-7":
                return o.getPcPosition().compareTo(this.getPcPosition());
            default:
                return o.getPcImpression().compareTo(this.getPcImpression());
        }
    }
}