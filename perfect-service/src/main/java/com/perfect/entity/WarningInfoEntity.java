package com.perfect.entity;

import java.util.Date;

/**
 * 预警信息
 * Created by john on 2014/8/8.
 */
public class WarningInfoEntity {

    private String id;

    private Long accountId;//百度账户id

    private Double percent;//百分率

    private String warningState;//预警状态（有：警示状态，预警边缘， 发展状态良好， 警示状态）

    private Date createTime;//创建时间

    private String warningSign;//预警标志(有:红色标记(red) 黄色标记(yellow) 绿色标记(green) 黑色标记(black))分别对应上面的预警状态

    private String suggest;//建议

    private Double Budget;

    public WarningInfoEntity(String id, Long accountId, Double percent, String warningState, Date createTime, String warningSign, String suggest, Double budget) {
        this.id = id;
        this.accountId = accountId;
        this.percent = percent;
        this.warningState = warningState;
        this.createTime = createTime;
        this.warningSign = warningSign;
        this.suggest = suggest;
        Budget = budget;
    }

    public WarningInfoEntity() {
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

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public String getWarningState() {
        return warningState;
    }

    public void setWarningState(String warningState) {
        this.warningState = warningState;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getWarningSign() {
        return warningSign;
    }

    public void setWarningSign(String warningSign) {
        this.warningSign = warningSign;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public Double getBudget() {
        return Budget;
    }

    public void setBudget(Double budget) {
        Budget = budget;
    }
}
