package com.perfect.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * 预警规则
 * Created by john on 2014/8/5.
 */
@Document(collection = "sys_warning")
public class WarningRuleEntity extends AccountIdEntity{

    @Id
    private String id;

    @Field("budgetType")
    private Integer budgetType;//预算类型

    @Field("budget")
    private Double budget;//预算金额

    @Field("warningPercent")
    private Double warningPercent;//预警百分率

    @Field("tels")
    private String tels; //手机号码

    @Field("mails")
    private String mails;//邮箱地址

    @Field("startTime")
    private Date startTime;//生效时间

    @Field("dayCountDate")
    private Date dayCountDate;//日统计时间

    @Field("isWarninged")
    private Integer isWarninged;//当天是否已经警告过（警告过为1，否则0）

    @Field("isEnable")
    private Integer isEnable;//是否启用


    public WarningRuleEntity(String id, Integer budgetType, Double budget, Double warningPercent, String tels, String mails, Date startTime, Date dayCountDate, Integer isWarninged, Integer isEnable) {
        this.id = id;
        this.budgetType = budgetType;
        this.budget = budget;
        this.warningPercent = warningPercent;
        this.tels = tels;
        this.mails = mails;
        this.startTime = startTime;
        this.dayCountDate = dayCountDate;
        this.isWarninged = isWarninged;
        this.isEnable = isEnable;
    }

    public WarningRuleEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getBudgetType() {
        return budgetType;
    }

    public void setBudgetType(Integer budgetType) {
        this.budgetType = budgetType;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public Double getWarningPercent() {
        return warningPercent;
    }

    public void setWarningPercent(Double warningPercent) {
        this.warningPercent = warningPercent;
    }

    public String getTels() {
        return tels;
    }

    public void setTels(String tels) {
        this.tels = tels;
    }

    public String getMails() {
        return mails;
    }

    public void setMails(String mails) {
        this.mails = mails;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getDayCountDate() {
        return dayCountDate;
    }

    public void setDayCountDate(Date dayCountDate) {
        this.dayCountDate = dayCountDate;
    }

    public Integer getIsWarninged() {
        return isWarninged;
    }

    public void setIsWarninged(Integer isWarninged) {
        this.isWarninged = isWarninged;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }
}
