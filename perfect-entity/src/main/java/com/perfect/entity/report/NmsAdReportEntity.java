package com.perfect.entity.report;

import com.perfect.entity.account.AccountIdEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

import static com.perfect.commons.constants.MongoEntityConstants.CAMPAIGN_ID;

/**
 * Created by dolphineor on 2015-7-20.
 */
public class NmsAdReportEntity extends AccountIdEntity {

    @Id
    private String id;

    @Field(value = CAMPAIGN_ID)
    private Long campaignId;

    @Field(value = "cpna")
    private String campaignName;

    @Field(value = "groupid")
    private Long groupId; // 推广组ID

    @Field(value = "groupn")
    private String groupName;   // 推广组名称

    @Field(value = "adid")
    private Long adId;  // 创意ID

    @Field(value = "adtit")
    private String adTitle; // 创意标题

    @Field(value = "adtype")
    private Integer adType; // 创意类型

    @Field(value = "impr")
    private Integer impression;     // 展现次数

    @Field(value = "click")
    private Integer click;      // 点击次数

    @Field(value = "ctr")
    private Double ctr;     // 点击率=点击次数/展现次数

    @Field(value = "cost")
    private BigDecimal cost;        // 消费

    @Field(value = "cpm")
    private BigDecimal cpm;       // 千次展现消费

    @Field(value = "acp")
    private BigDecimal acp;     // 平均点击价格=消费/点击次数

    @Field(value = "srchuv")
    private Integer srchuv;     // 展现独立访客

    @Field(value = "clickuv")
    private Integer clickuv;    // 点击独立访客

    @Field(value = "srsur")
    private Double srsur;  // 展现频次

    @Field(value = "cusur")
    private Double cusur;  // 独立访客点击率

    @Field(value = "cocur")
    private BigDecimal cocur;  // 平均独立访客点击价格

    @Field(value = "ar")
    private Double arrivalRate; // 抵达率

    @Field(value = "hr")
    private Double hopRate;    // 二跳率

    @Field(value = "art")
    private Long avgResTime;  // 平均访问时间

    @Field(value = "dt")
    private Integer directTrans;    // 直接转化

    @Field(value = "idt")
    private Integer indirectTrans;  // 间接转化


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public String getAdTitle() {
        return adTitle;
    }

    public void setAdTitle(String adTitle) {
        this.adTitle = adTitle;
    }

    public Integer getAdType() {
        return adType;
    }

    public void setAdType(Integer adType) {
        this.adType = adType;
    }

    public Integer getImpression() {
        return impression;
    }

    public void setImpression(Integer impression) {
        this.impression = impression;
    }

    public Integer getClick() {
        return click;
    }

    public void setClick(Integer click) {
        this.click = click;
    }

    public Double getCtr() {
        return ctr;
    }

    public void setCtr(Double ctr) {
        this.ctr = ctr;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getCpm() {
        return cpm;
    }

    public void setCpm(BigDecimal cpm) {
        this.cpm = cpm;
    }

    public BigDecimal getAcp() {
        return acp;
    }

    public void setAcp(BigDecimal acp) {
        this.acp = acp;
    }

    public Integer getSrchuv() {
        return srchuv;
    }

    public void setSrchuv(Integer srchuv) {
        this.srchuv = srchuv;
    }

    public Integer getClickuv() {
        return clickuv;
    }

    public void setClickuv(Integer clickuv) {
        this.clickuv = clickuv;
    }

    public Double getSrsur() {
        return srsur;
    }

    public void setSrsur(Double srsur) {
        this.srsur = srsur;
    }

    public Double getCusur() {
        return cusur;
    }

    public void setCusur(Double cusur) {
        this.cusur = cusur;
    }

    public BigDecimal getCocur() {
        return cocur;
    }

    public void setCocur(BigDecimal cocur) {
        this.cocur = cocur;
    }

    public Double getArrivalRate() {
        return arrivalRate;
    }

    public void setArrivalRate(Double arrivalRate) {
        this.arrivalRate = arrivalRate;
    }

    public Double getHopRate() {
        return hopRate;
    }

    public void setHopRate(Double hopRate) {
        this.hopRate = hopRate;
    }

    public Long getAvgResTime() {
        return avgResTime;
    }

    public void setAvgResTime(Long avgResTime) {
        this.avgResTime = avgResTime;
    }

    public Integer getDirectTrans() {
        return directTrans;
    }

    public void setDirectTrans(Integer directTrans) {
        this.directTrans = directTrans;
    }

    public Integer getIndirectTrans() {
        return indirectTrans;
    }

    public void setIndirectTrans(Integer indirectTrans) {
        this.indirectTrans = indirectTrans;
    }


    @Override
    public String toString() {
        return "NmsAdReportEntity{" +
                "id='" + id + '\'' +
                ", campaignId=" + campaignId +
                ", campaignName='" + campaignName + '\'' +
                ", groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", adId=" + adId +
                ", adTitle='" + adTitle + '\'' +
                ", adType=" + adType +
                ", impression=" + impression +
                ", click=" + click +
                ", ctr=" + ctr +
                ", cost=" + cost +
                ", cpm=" + cpm +
                ", acp=" + acp +
                ", srchuv=" + srchuv +
                ", clickuv=" + clickuv +
                ", srsur=" + srsur +
                ", cusur=" + cusur +
                ", cocur=" + cocur +
                ", arrivalRate=" + arrivalRate +
                ", hopRate=" + hopRate +
                ", avgResTime=" + avgResTime +
                ", directTrans=" + directTrans +
                ", indirectTrans=" + indirectTrans +
                '}';
    }
}