package com.perfect.entity;

import com.perfect.mongodb.utils.EntityConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

/**
 * Created by XiaoWei on 2014/9/22.
 */
@Document(collection = EntityConstants.TBL_IMPORTANT_KEYWORD)
public class KeywordImEntity extends KeywordEntity {
    @Field(value = "cgId")
    private String customGroupId;
    @Field(value = "im")
    private Integer impression = 0;     //PC展现次数
    @Field(value = "click")
    private Integer click = 0;      //PC点击次数
    @Field(value = "ctr")
    private Double ctr = 0.0;     //PC点击次数/展现次数
    @Field(value = "cost")
    private BigDecimal cost = BigDecimal.valueOf(0.0);        //PC消费
    @Field(value = "cpc")
    private BigDecimal cpc = BigDecimal.valueOf(0.0);     //PC平均点击价格=消费/点击次数
    @Field(value = "cpm")
    private BigDecimal cpm = BigDecimal.valueOf(0.0);       //PC千次展现消费
    private Double conversion = 0.0;
    private int currentRank = 0;
    @Field(value = "pcqu")
    private int pcQuality = 0;
    @Field(value = "mqu")
    private int mQuality = 0;
    @Field(value = "bs")
    private int biddingStatus = 0;
    @Field(value = "rule")
    private boolean rule = false;
    private String ruleDesc;
    @Field(value = "ss")
    private String statusStr;

    public String getCustomGroupId() {
        return customGroupId;
    }

    public void setCustomGroupId(String customGroupId) {
        this.customGroupId = customGroupId;
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

    public int getmQuality() {
        return mQuality;
    }

    public void setmQuality(int mQuality) {
        this.mQuality = mQuality;
    }

    public int getBiddingStatus() {
        return biddingStatus;
    }

    public void setBiddingStatus(int biddingStatus) {
        this.biddingStatus = biddingStatus;
    }

    public boolean isRule() {
        return rule;
    }

    public void setRule(boolean rule) {
        this.rule = rule;
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
}
