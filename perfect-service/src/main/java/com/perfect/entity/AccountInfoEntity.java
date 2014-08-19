/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.entity;


import com.perfect.autosdk.common.OptType;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Deprecated
@Document(collection = "AccountInfoType")
public class AccountInfoEntity {
    private Long userid;
    private Double balance;
    private Double cost;
    private Double payment;
    private Integer budgetType;
    private Double budget;
    private List<Integer> regionTarget;
    private List<String> excludeIp;
    private List<String> openDomains;
    private String regDomain;
    private List<OfflineTimeEntity> budgetOfflineTime;
    private List<Double> weeklyBudget;
    private Integer userStat;
    private Boolean isDynamicCreative;
    private OptType opt;

    public boolean setUserid(Long aUserid) {
        boolean wasSet = false;
        userid = aUserid;
        wasSet = true;
        return wasSet;
    }

    public boolean setBalance(Double aBalance) {
        boolean wasSet = false;
        balance = aBalance;
        wasSet = true;
        return wasSet;
    }

    public boolean setCost(Double aCost) {
        boolean wasSet = false;
        cost = aCost;
        wasSet = true;
        return wasSet;
    }

    public boolean setPayment(Double aPayment) {
        boolean wasSet = false;
        payment = aPayment;
        wasSet = true;
        return wasSet;
    }

    public boolean setBudgetType(Integer aBudgetType) {
        boolean wasSet = false;
        budgetType = aBudgetType;
        wasSet = true;
        return wasSet;
    }

    public boolean setBudget(Double aBudget) {
        boolean wasSet = false;
        budget = aBudget;
        wasSet = true;
        return wasSet;
    }

    public boolean addRegionTarget(Integer aRegionTarget) {
        boolean wasAdded = false;
        wasAdded = regionTarget.add(aRegionTarget);
        return wasAdded;
    }

    public boolean removeRegionTarget(Integer aRegionTarget) {
        boolean wasRemoved = false;
        wasRemoved = regionTarget.remove(aRegionTarget);
        return wasRemoved;
    }

    public boolean addExcludeIp(String aExcludeIp) {
        boolean wasAdded = false;
        wasAdded = excludeIp.add(aExcludeIp);
        return wasAdded;
    }

    public boolean removeExcludeIp(String aExcludeIp) {
        boolean wasRemoved = false;
        wasRemoved = excludeIp.remove(aExcludeIp);
        return wasRemoved;
    }

    public boolean addOpenDomain(String aOpenDomain) {
        boolean wasAdded = false;
        wasAdded = openDomains.add(aOpenDomain);
        return wasAdded;
    }

    public boolean removeOpenDomain(String aOpenDomain) {
        boolean wasRemoved = false;
        wasRemoved = openDomains.remove(aOpenDomain);
        return wasRemoved;
    }

    public boolean setRegDomain(String aRegDomain) {
        boolean wasSet = false;
        regDomain = aRegDomain;
        wasSet = true;
        return wasSet;
    }

    public boolean addBudgetOfflineTime(OfflineTimeEntity aBudgetOfflineTime) {
        boolean wasAdded = false;
        wasAdded = budgetOfflineTime.add(aBudgetOfflineTime);
        return wasAdded;
    }

    public boolean removeBudgetOfflineTime(OfflineTimeEntity aBudgetOfflineTime) {
        boolean wasRemoved = false;
        wasRemoved = budgetOfflineTime.remove(aBudgetOfflineTime);
        return wasRemoved;
    }

    public boolean addWeeklyBudget(Double aWeeklyBudget) {
        boolean wasAdded = false;
        wasAdded = weeklyBudget.add(aWeeklyBudget);
        return wasAdded;
    }

    public boolean removeWeeklyBudget(Double aWeeklyBudget) {
        boolean wasRemoved = false;
        wasRemoved = weeklyBudget.remove(aWeeklyBudget);
        return wasRemoved;
    }

    public boolean setUserStat(Integer aUserStat) {
        boolean wasSet = false;
        userStat = aUserStat;
        wasSet = true;
        return wasSet;
    }

    public boolean setIsDynamicCreative(Boolean aIsDynamicCreative) {
        boolean wasSet = false;
        isDynamicCreative = aIsDynamicCreative;
        wasSet = true;
        return wasSet;
    }

    public boolean setOpt(OptType aOpt) {
        boolean wasSet = false;
        opt = aOpt;
        wasSet = true;
        return wasSet;
    }

    public Long getUserid() {
        return userid;
    }

    public Double getBalance() {
        return balance;
    }

    public Double getCost() {
        return cost;
    }

    public Double getPayment() {
        return payment;
    }

    public Integer getBudgetType() {
        return budgetType;
    }

    public Double getBudget() {
        return budget;
    }

    public Integer getRegionTarget(int index) {
        Integer aRegionTarget = regionTarget.get(index);
        return aRegionTarget;
    }

    public List<Integer> getRegionTarget() {
        return regionTarget;
    }

    public void setRegionTarget(List<Integer> aregionTarget) {
        regionTarget = aregionTarget;
    }

    public int numberOfRegionTarget() {
        int number = regionTarget.size();
        return number;
    }

    public boolean hasRegionTarget() {
        boolean has = regionTarget.size() > 0;
        return has;
    }

    public int indexOfRegionTarget(Integer aRegionTarget) {
        int index = regionTarget.indexOf(aRegionTarget);
        return index;
    }

    public String getExcludeIp(int index) {
        String aExcludeIp = excludeIp.get(index);
        return aExcludeIp;
    }

    public List<String> getExcludeIp() {
        return excludeIp;
    }

    public void setExcludeIp(List<String> aexcludeIp) {
        excludeIp = aexcludeIp;
    }

    public int numberOfExcludeIp() {
        int number = excludeIp.size();
        return number;
    }

    public boolean hasExcludeIp() {
        boolean has = excludeIp.size() > 0;
        return has;
    }

    public int indexOfExcludeIp(String aExcludeIp) {
        int index = excludeIp.indexOf(aExcludeIp);
        return index;
    }

    public String getOpenDomain(int index) {
        String aOpenDomain = openDomains.get(index);
        return aOpenDomain;
    }

    public List<String> getOpenDomains() {
        return openDomains;
    }

    public void setOpenDomains(List<String> aopenDomains) {
        openDomains = aopenDomains;
    }

    public int numberOfOpenDomains() {
        int number = openDomains.size();
        return number;
    }

    public boolean hasOpenDomains() {
        boolean has = openDomains.size() > 0;
        return has;
    }

    public int indexOfOpenDomain(String aOpenDomain) {
        int index = openDomains.indexOf(aOpenDomain);
        return index;
    }

    public String getRegDomain() {
        return regDomain;
    }

    public OfflineTimeEntity getBudgetOfflineTime(int index) {
        OfflineTimeEntity aBudgetOfflineTime = budgetOfflineTime.get(index);
        return aBudgetOfflineTime;
    }

    public List<OfflineTimeEntity> getBudgetOfflineTime() {
        return budgetOfflineTime;
    }

    public void setBudgetOfflineTime(List<OfflineTimeEntity> abudgetOfflineTime) {
        budgetOfflineTime = abudgetOfflineTime;
    }

    public int numberOfBudgetOfflineTime() {
        int number = budgetOfflineTime.size();
        return number;
    }

    public boolean hasBudgetOfflineTime() {
        boolean has = budgetOfflineTime.size() > 0;
        return has;
    }

    public int indexOfBudgetOfflineTime(OfflineTimeEntity aBudgetOfflineTime) {
        int index = budgetOfflineTime.indexOf(aBudgetOfflineTime);
        return index;
    }

    public Double getWeeklyBudget(int index) {
        Double aWeeklyBudget = weeklyBudget.get(index);
        return aWeeklyBudget;
    }

    public List<Double> getWeeklyBudget() {
        return weeklyBudget;
    }

    public void setWeeklyBudget(List<Double> aweeklyBudget) {
        weeklyBudget = aweeklyBudget;
    }

    public int numberOfWeeklyBudget() {
        int number = weeklyBudget.size();
        return number;
    }

    public boolean hasWeeklyBudget() {
        boolean has = weeklyBudget.size() > 0;
        return has;
    }

    public int indexOfWeeklyBudget(Double aWeeklyBudget) {
        int index = weeklyBudget.indexOf(aWeeklyBudget);
        return index;
    }

    public Integer getUserStat() {
        return userStat;
    }

    public Boolean getIsDynamicCreative() {
        return isDynamicCreative;
    }

    public OptType getOpt() {
        return opt;
    }


    public void delete() {
    }


    public String toString() {
        String outputString = "";
        return super.toString() + "[" +
                "userid" + ":" + getUserid() + "," +
                "balance" + ":" + getBalance() + "," +
                "cost" + ":" + getCost() + "," +
                "payment" + ":" + getPayment() + "," +
                "budgetType" + ":" + getBudgetType() + "," +
                "budget" + ":" + getBudget() + "," +
                "regDomain" + ":" + getRegDomain() + "," +
                "userStat" + ":" + getUserStat() + "," +
                "isDynamicCreative" + ":" + getIsDynamicCreative() + "]" + System.getProperties().getProperty("line.separator") +
                "  " + "opt" + "=" + (getOpt() != null ? !getOpt().equals(this) ? getOpt().toString().replaceAll("  ", "    ") : "this" : "null")
                + outputString;
    }
}