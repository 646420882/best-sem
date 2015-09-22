package com.perfect.dto.baidu;

import java.util.List;

/**
 * Created by baizz on 2014-11-26.
 */
public class BaiduAccountInfoDTO {

    private Long id;

    private String baiduUserName;

    private String baiduRemarkName;

    private String baiduPassword;

    private String token;

    private Boolean dfault = false;

    private Double balance;

    private Double cost;

    private Double payment;

    private Integer budgetType;

    private Double budget;

    private List<Integer> regionTarget;

    private List<String> excludeIp;

    private List<String> openDomains;

    private String regDomain;

    private List<OfflineTimeDTO> budgetOfflineTime;

    private List<Double> weeklyBudget;

    private Integer userStat;

    private Boolean isDynamicCreative;

    private String dynamicCreativeParam;

    private OptTypeDTO opt;

    //该账户下的百度帐号是否启用   启用：1  停用：0
    private Long state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBaiduUserName() {
        return baiduUserName;
    }

    public void setBaiduUserName(String baiduUserName) {
        this.baiduUserName = baiduUserName;
    }

    public String getBaiduPassword() {
        return baiduPassword;
    }

    public void setBaiduPassword(String baiduPassword) {
        this.baiduPassword = baiduPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
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

    public List<Integer> getRegionTarget() {
        return regionTarget;
    }

    public void setRegionTarget(List<Integer> regionTarget) {
        this.regionTarget = regionTarget;
    }

    public List<String> getExcludeIp() {
        return excludeIp;
    }

    public void setExcludeIp(List<String> excludeIp) {
        this.excludeIp = excludeIp;
    }

    public List<String> getOpenDomains() {
        return openDomains;
    }

    public void setOpenDomains(List<String> openDomains) {
        this.openDomains = openDomains;
    }

    public String getRegDomain() {
        return regDomain;
    }

    public void setRegDomain(String regDomain) {
        this.regDomain = regDomain;
    }

    public List<OfflineTimeDTO> getBudgetOfflineTime() {
        return budgetOfflineTime;
    }

    public void setBudgetOfflineTime(List<OfflineTimeDTO> budgetOfflineTime) {
        this.budgetOfflineTime = budgetOfflineTime;
    }

    public List<Double> getWeeklyBudget() {
        return weeklyBudget;
    }

    public void setWeeklyBudget(List<Double> weeklyBudget) {
        this.weeklyBudget = weeklyBudget;
    }

    public Integer getUserStat() {
        return userStat;
    }

    public void setUserStat(Integer userStat) {
        this.userStat = userStat;
    }

    public Boolean getIsDynamicCreative() {
        return isDynamicCreative;
    }

    public void setIsDynamicCreative(Boolean isDynamicCreative) {
        this.isDynamicCreative = isDynamicCreative;
    }

    public String getDynamicCreativeParam() {
        return dynamicCreativeParam;
    }

    public void setDynamicCreativeParam(String dynamicCreativeParam) {
        this.dynamicCreativeParam = dynamicCreativeParam;
    }

    public OptTypeDTO getOpt() {
        return opt;
    }

    public void setOpt(OptTypeDTO opt) {
        this.opt = opt;
    }

    public boolean isDfault() {
        return dfault;
    }

    public void setDfault(boolean dfault) {
        this.dfault = dfault;
    }

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    public String getBaiduRemarkName() {
        return baiduRemarkName;
    }

    public void setBaiduRemarkName(String baiduRemarkName) {
        this.baiduRemarkName = baiduRemarkName;
    }
}
