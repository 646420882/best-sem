package com.perfect.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * Created by baizz on 2014-07-30.
 */
@Document(collection = "accountRealTimeData")
public class AccountRealTimeDataVOEntity {

    private Long accountId;

    @Field(value = "name")
    private String accountName;

    private Date date;

    @Field(value = "impr")
    private Integer impression;     //展现次数

    private Integer click;      //点击次数

    private Double ctr;     //点击率=点击次数/展现次数

    private Double cost;        //消费

    private Double cpc;     //平均点击价格=消费/点击次数

    @Field(value = "conv")
    private Double conversion;      //转化

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
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

    public Double getConversion() {
        return conversion;
    }

    public void setConversion(Double conversion) {
        this.conversion = conversion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountRealTimeDataVOEntity that = (AccountRealTimeDataVOEntity) o;

        if (accountId != null ? !accountId.equals(that.accountId) : that.accountId != null) return false;
        if (accountName != null ? !accountName.equals(that.accountName) : that.accountName != null) return false;
        if (click != null ? !click.equals(that.click) : that.click != null) return false;
        if (conversion != null ? !conversion.equals(that.conversion) : that.conversion != null) return false;
        if (cost != null ? !cost.equals(that.cost) : that.cost != null) return false;
        if (cpc != null ? !cpc.equals(that.cpc) : that.cpc != null) return false;
        if (ctr != null ? !ctr.equals(that.ctr) : that.ctr != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (impression != null ? !impression.equals(that.impression) : that.impression != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = accountId != null ? accountId.hashCode() : 0;
        result = 31 * result + (accountName != null ? accountName.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (impression != null ? impression.hashCode() : 0);
        result = 31 * result + (click != null ? click.hashCode() : 0);
        result = 31 * result + (ctr != null ? ctr.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        result = 31 * result + (cpc != null ? cpc.hashCode() : 0);
        result = 31 * result + (conversion != null ? conversion.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AccountRealTimeDataVOEntity{" +
                "accountId=" + accountId +
                ", accountName='" + accountName + '\'' +
                ", date=" + date +
                ", impression=" + impression +
                ", click=" + click +
                ", ctr=" + ctr +
                ", cost=" + cost +
                ", cpc=" + cpc +
                ", conversion=" + conversion +
                '}';
    }
}
