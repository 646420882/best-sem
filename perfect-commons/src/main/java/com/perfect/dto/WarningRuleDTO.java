package com.perfect.dto;

import com.perfect.dto.account.AccountIdDTO;

import java.util.Date;

/**
 * Created by XiaoWei on 2014/11/28.
 * 2014-11-28 refactor XiaoWei code by john
 */
public class WarningRuleDTO extends AccountIdDTO {
    private String id;
    private Integer budgetType;//预算类型
    private Double budget;//预算金额
    private Double warningPercent;//预警百分率
    private String tels; //手机号码
    private String mails;//邮箱地址
    private Date startTime;//生效时间
    private Integer isWarninged;//当天是否已经警告过（警告过为1，否则0）
    private Integer isEnable;//是否启用
    private String systemUserName;//系统用户名

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

    public String getSystemUserName() {
        return systemUserName;
    }

    public void setSystemUserName(String systemUserName) {
        this.systemUserName = systemUserName;
    }

    public WarningRuleDTO() {
    }

    public WarningRuleDTO(String id, Integer budgetType, Double budget, Double warningPercent, String tels, String mails, Date startTime, Integer isWarninged, Integer isEnable, String systemUserName) {
        this.id = id;
        this.budgetType = budgetType;
        this.budget = budget;
        this.warningPercent = warningPercent;
        this.tels = tels;
        this.mails = mails;
        this.startTime = startTime;
        this.isWarninged = isWarninged;
        this.isEnable = isEnable;
        this.systemUserName = systemUserName;
    }
}
