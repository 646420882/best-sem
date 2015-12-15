package com.perfect.entity.sys;

import com.perfect.entity.baidu.OfflineTimeEntity;
import com.perfect.entity.baidu.OptTypeEntity;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * 改名成ModuleAccountInfoEntity
 */
public class ModuleAccountInfoEntity {

    private Long id;

    private String baiduUserName;//百度账号

    private String baiduRemarkName;//百度账户备注名

    private String baiduPassword;//百度账号密码

    private String token;//百度的代码标识

    private String hyToken;

    @Field("dft")
    private Boolean dfault = false;//是否是默认加载账号

    @Field("b")
    private Double balance;//账户余额

    @Field("c")
    private Double cost;//账户积累消费

    @Field("pay")
    private Double payment;//账户投资

    @Field("bgtt")
    private Integer budgetType;//账户预算类型，0不设预算，1日预算，2周预算

    @Field("bgt")
    private Double budget;//账户预算

    @Field("rt")
    private List<Integer> regionTarget;//推广地域列表

    @Field("exIp")
    private List<String> excludeIp;//ip排除列表

    @Field("od")
    private List<String> openDomains;//账户开放域名列表

    @Field("rd")
    private String regDomain;//账户注册域名

    @Field("bot")
    private List<OfflineTimeEntity> budgetOfflineTime;//到达预算下线时段

    @Field("wb")
    private List<Double> weeklyBudget;//返回本周的每日预算值

    @Field("us")
    private Integer userStat;//账户状态

    @Field("dc")
    private Boolean isDynamicCreative;//是否开启动态创意 

    @Field("dcp")
    private String dynamicCreativeParam;//动态创意统计参数

    @Field("o")
    private OptTypeEntity opt;

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

    public List<OfflineTimeEntity> getBudgetOfflineTime() {
        return budgetOfflineTime;
    }

    public void setBudgetOfflineTime(List<OfflineTimeEntity> budgetOfflineTime) {
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

    public OptTypeEntity getOpt() {
        return opt;
    }

    public void setOpt(OptTypeEntity opt) {
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

    public String getHyToken() {
        return hyToken;
    }

    public void setHyToken(String hyToken) {
        this.hyToken = hyToken;
    }
}
