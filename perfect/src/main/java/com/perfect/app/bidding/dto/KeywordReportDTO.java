package com.perfect.app.bidding.dto;

import com.perfect.entity.KeywordEntity;

/**
 * Created by vbzer_000 on 2014/8/25.
 */
public class KeywordReportDTO extends KeywordEntity {

    private Integer impression;     //PC展现次数

    private Integer click;      //PC点击次数

    private Double ctr;     //PC点击次数/展现次数

    private Double cost;        //PC消费

    private Double cpc;     //PC平均点击价格=消费/点击次数

    private Double cpm;       //PC千次展现消费

    private Double conversion;

    private int currentRank;

    private int pcQuality;

    private int mQuality;

    private int biddingStatus;

    private boolean rule;

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

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getCpc() {
        return cpc;
    }

    public void setCpc(Double cpc) {
        this.cpc = cpc;
    }

    public Double getCpm() {
        return cpm;
    }

    public void setCpm(Double cpm) {
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
}
