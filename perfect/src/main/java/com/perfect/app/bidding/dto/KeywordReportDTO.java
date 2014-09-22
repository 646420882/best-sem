package com.perfect.app.bidding.dto;

import com.perfect.entity.KeywordEntity;

import java.math.BigDecimal;

/**
 * Created by vbzer_000 on 2014/8/25.
 */
public class KeywordReportDTO extends KeywordEntity {

    private String campaignName;

    private String adgroupName;

    private Integer impression = 0;     //PC展现次数

    private Integer click = 0;      //PC点击次数

    private Double ctr = 0.0;     //PC点击次数/展现次数

    private BigDecimal cost = BigDecimal.valueOf(0.0);        //PC消费

    private BigDecimal cpc = BigDecimal.valueOf(0.0);     //PC平均点击价格=消费/点击次数

    private BigDecimal cpm = BigDecimal.valueOf(0.0);       //PC千次展现消费

    private Double conversion = 0.0;

    private int currentRank = 0;

    private int pcQuality = 0;

    private int mQuality = 0;

    private int biddingStatus = 0;

    private boolean rule = false;

    private String ruleDesc;

    private String statusStr;

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

    public BigDecimal getCpc() {
        return cpc;
    }

    public void setCpc(BigDecimal cpc) {
        this.cpc = cpc;
    }

    public BigDecimal getCpm() {
        return cpm;
    }

    public void setCpm(BigDecimal cpm) {
        this.cpm = cpm;
    }

    public Double getConversion() {
        return conversion;
    }

    public void setConversion(Double conversion) {
        this.conversion = conversion;
    }

    public int getCurrentRank() {
        return currentRank;
    }

    public void setCurrentRank(int currentRank) {
        this.currentRank = currentRank;
    }

    public int getPcQuality() {
        return pcQuality;
    }

    public void setPcQuality(int pcQuality) {
        this.pcQuality = pcQuality;
    }

    public int getBiddingStatus() {
        return biddingStatus;
    }

    public void setBiddingStatus(int biddingStatus) {
        this.biddingStatus = biddingStatus;
    }

    public boolean getRule() {
        return rule;
    }

    public void setRule(boolean rule) {
        this.rule = rule;
    }

    public int getmQuality() {
        return mQuality;
    }

    public void setmQuality(int mQuality) {
        this.mQuality = mQuality;
    }

    public String getRuleDesc() {
        return ruleDesc;
    }

    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getAdgroupName() {
        return adgroupName;
    }

    public void setAdgroupName(String adgroupName) {
        this.adgroupName = adgroupName;
    }
}
