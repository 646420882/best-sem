package com.perfect.entity;

import com.perfect.autosdk.common.OptType;
import com.perfect.autosdk.sms.v3.OfflineTimeType;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * Created by vbzer_000 on 2014/6/18.
 */
public class BaiduAccountInfoEntity {

    private Long id;

    private String baiduUserName;

    private String baiduPassword;

    private String token;

    @Field("dft")
    private Boolean dfault = false;

    @Field("b")
    private Double balance;

    @Field("c")
    private Double cost;

    @Field("pay")
    private Double payment;

    @Field("bgtt")
    private Integer budgetType;

    @Field("bgt")
    private Double budget;

    @Field("rt")
    private List<Integer> regionTarget;

    @Field("exIp")
    private List<String> excludeIp;

    @Field("od")
    private List<String> openDomains;

    @Field("rd")
    private String regDomain;

    @Field("bot")
    private List<OfflineTimeType> budgetOfflineTime;

    @Field("wb")
    private List<Double> weeklyBudget;

    @Field("us")
    private Integer userStat;

    @Field("dc")
    private Boolean isDynamicCreative;

    @Field("dcp")
    private String dynamicCreativeParam;

    @Field("o")
    private OptType opt;

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

    public List<OfflineTimeType> getBudgetOfflineTime() {
        return budgetOfflineTime;
    }

    public void setBudgetOfflineTime(List<OfflineTimeType> budgetOfflineTime) {
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

    public OptType getOpt() {
        return opt;
    }

    public void setOpt(OptType opt) {
        this.opt = opt;
    }

    public boolean isDfault() {
        return dfault;
    }

    public void setDfault(boolean dfault) {
        this.dfault = dfault;
    }
}
