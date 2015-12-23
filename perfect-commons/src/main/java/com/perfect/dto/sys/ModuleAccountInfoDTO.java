package com.perfect.dto.sys;

import com.perfect.dto.baidu.OfflineTimeDTO;
import com.perfect.dto.baidu.OptTypeDTO;

import java.util.List;

/**
 * Created by yousheng on 15/12/15.
 */
public class ModuleAccountInfoDTO {

    private String id;

    private String userId;                              // 系统用户的Mongo ID

    private String moduleId;                            // 模块ID

    private String accountPlatformType;                 // 账号所属平台

    private Long accountBindingTime;                    // 账号绑定时间

    private Long baiduAccountId;                        // 百度账号ID

    private String baiduUserName;                       // 百度账号

    private String baiduRemarkName;                     // 百度账户备注名

    private String baiduPassword;                       // 百度账号密码

    private String token;                               // 百度推广的代码标识

    private String hyToken;

    private Boolean dfault = false;                     // 是否是默认加载账号

    private Double balance;                             // 账户余额

    private Double cost;                                // 账户积累消费

    private Double payment;                             // 账户投资

    private Integer budgetType;                         // 账户预算类型，0不设预算，1日预算，2周预算

    private Double budget;                              // 账户预算

    private List<Integer> regionTarget;                 // 推广地域列表

    private List<String> excludeIp;                     // ip排除列表

    private List<String> openDomains;                   // 账户开放域名列表

    private String regDomain;                           // 账户注册域名

    private String bestRegDomain;                       // 百思注册时网站url

    private List<OfflineTimeDTO> budgetOfflineTime;     // 到达预算下线时段

    private List<Double> weeklyBudget;                  // 返回本周的每日预算值

    private Integer userStat;                           // 账户状态

    private Boolean isDynamicCreative;                  // 是否开启动态创意

    private String dynamicCreativeParam;                // 动态创意统计参数

    private OptTypeDTO opt;

    private Long state;                                 // 该账户下的百度帐号是否启用  启用：1  停用：0

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getAccountPlatformType() {
        return accountPlatformType;
    }

    public void setAccountPlatformType(String accountPlatformType) {
        this.accountPlatformType = accountPlatformType;
    }

    public Long getAccountBindingTime() {
        return accountBindingTime;
    }

    public void setAccountBindingTime(Long accountBindingTime) {
        this.accountBindingTime = accountBindingTime;
    }

    public Long getBaiduAccountId() {
        return baiduAccountId;
    }

    public void setBaiduAccountId(Long baiduAccountId) {
        this.baiduAccountId = baiduAccountId;
    }

    public Boolean isDfault() {
        return dfault;
    }

    public void setDfault(Boolean dfault) {
        this.dfault = dfault;
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

    public Boolean isDynamicCreative() {
        return isDynamicCreative;
    }

    public void setDynamicCreative(Boolean isDynamicCreative) {
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

    public String getBestRegDomain() {
        return bestRegDomain;
    }

    public void setBestRegDomain(String bestRegDomain) {
        this.bestRegDomain = bestRegDomain;
    }
}
