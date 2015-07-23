package com.perfect.entity.report;

import com.perfect.entity.account.AccountIdEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.util.Date;

import static com.perfect.commons.constants.MongoEntityConstants.TBL_NMS_ACCOUNT_REPORT;

/**
 * Created by subdong on 15-7-20.
 */
@Document(collection = TBL_NMS_ACCOUNT_REPORT)
public class NmsAccountReportEntity extends AccountIdEntity {

    @Id
    private String id;

    @Field(value = "acna")
    private String accountName;     //账户

    @Field(value = "date")
    private Date date;

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
    private Double clickuv;    // 点击独立访客

    @Field(value = "srsur")
    private Integer srsur;  // 展现频次

    @Field(value = "cusur")
    private Double cusur;  // 独立访客点击率

    @Field(value = "cocur")
    private BigDecimal cocur;  // 平均独立访客点击价格

    @Field(value = "ar")
    private Double arrivalRate; // 抵达率

    @Field(value = "hr")
    private Double hopRate;    // 二跳率

    @Field(value = "art")
    private Double avgResTime;  // 平均访问时间

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

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public Double getClickuv() {
        return clickuv;
    }

    public void setClickuv(Double clickuv) {
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

    public Double getAvgResTime() {
        return avgResTime;
    }

    public void setAvgResTime(Double avgResTime) {
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
        return "NmsAccountReportEntity{" +
                "id='" + id + '\'' +
                ", accountName='" + accountName + '\'' +
                ", date=" + date +
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
