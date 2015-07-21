package com.perfect.dto.account;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by subdong on 15-7-21.
 */
public class NmsAccountReportDTO extends AccountIdDTO{
    private String id;

    private Date date;

    private String accountName;

    private Integer impression;     // 展现次数

    private Integer click;      // 点击次数

    private Double ctr;     // 点击率=点击次数/展现次数

    private BigDecimal cost;        // 消费

    private BigDecimal cpm;       // 千次展现消费

    private BigDecimal acp;     // 平均点击价格=消费/点击次数

    private Integer srchuv;     // 展现独立访客

    private Integer clickuv;    // 点击独立访客

    private Integer srsur;  // 展现频次

    private Double cusur;  // 独立访客点击率

    private BigDecimal cocur;  // 平均独立访客点击价格

    private Double arrivalRate; // 抵达率

    private Double hopRate;    // 二跳率

    private Long avgResTime;  // 平均访问时间

    private Integer directTrans;    // 直接转化

    private Integer indirectTrans;  // 间接转化

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
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

    public Integer getSrsur() {
        return srsur;
    }

    public void setSrsur(Integer srsur) {
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
        return "NmsAccountReportDTO{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", accountName='" + accountName + '\'' +
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
