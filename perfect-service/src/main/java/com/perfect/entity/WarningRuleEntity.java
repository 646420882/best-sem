package com.perfect.entity;

import java.util.Date;

/**
 * 预警规则
 * Created by john on 2014/8/5.
 */
public class WarningRuleEntity {

    private String id;

    private Long accountId;//百度账户id

    private Integer budgetType;//预算类型

    private Double budget;//预算金额

    private Double warningPercent;//预警百分率

    private String tels; //手机号码

    private String mails;//邮箱地址

    private Date startTime;//生效时间

    private Date dayCountDate;//日统计时间

    private Integer isWarninged;//当天是否已经警告过（警告过为1，否则0）

    private Integer isEnable;//是否启用


    public WarningRuleEntity(String id, Long accountId, Integer budgetType, Double budget, Double warningPercent, String tels, String mails, Date startTime, Date dayCountDate, Integer isWarninged, Integer isEnable) {
        this.id = id;
        this.accountId = accountId;
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

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
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
