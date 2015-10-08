/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.*;

// line 17 "../../../../../../../SDK.ump"
public class AccountInfo
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //AccountInfo Attributes
  private Long userId;
  private Double balance;
  private Double cost;
  private Double payment;
  private Double budget;
  private List<Integer> regionTarget;
  private List<String> excludeIp;
  private List<String> openDomains;
  private Integer budgetType;
  private String regDomain;
  private List<Double> weeklyBudget;
  private Integer userStat;
  private List<OfflineTimeType> budgetOfflineTime;
  private Boolean isDynamicCreative;
  private String dynamicCreativeParam;
  private Boolean isDynamicTagSublink;
  private Boolean isDynamicTitle;
  private Boolean isDynamicHotRedirect;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setUserId(Long aUserId)
  {
    boolean wasSet = false;
    userId = aUserId;
    wasSet = true;
    return wasSet;
  }

  public boolean setBalance(Double aBalance)
  {
    boolean wasSet = false;
    balance = aBalance;
    wasSet = true;
    return wasSet;
  }

  public boolean setCost(Double aCost)
  {
    boolean wasSet = false;
    cost = aCost;
    wasSet = true;
    return wasSet;
  }

  public boolean setPayment(Double aPayment)
  {
    boolean wasSet = false;
    payment = aPayment;
    wasSet = true;
    return wasSet;
  }

  public boolean setBudget(Double aBudget)
  {
    boolean wasSet = false;
    budget = aBudget;
    wasSet = true;
    return wasSet;
  }

  public void setRegionTarget(List<Integer> aregionTarget){
    regionTarget = aregionTarget;
  }

  public boolean addRegionTarget(Integer aRegionTarget)
  {
    boolean wasAdded = false;
    wasAdded = regionTarget.add(aRegionTarget);
    return wasAdded;
  }

  public boolean removeRegionTarget(Integer aRegionTarget)
  {
    boolean wasRemoved = false;
    wasRemoved = regionTarget.remove(aRegionTarget);
    return wasRemoved;
  }

  public void setExcludeIp(List<String> aexcludeIp){
    excludeIp = aexcludeIp;
  }

  public boolean addExcludeIp(String aExcludeIp)
  {
    boolean wasAdded = false;
    wasAdded = excludeIp.add(aExcludeIp);
    return wasAdded;
  }

  public boolean removeExcludeIp(String aExcludeIp)
  {
    boolean wasRemoved = false;
    wasRemoved = excludeIp.remove(aExcludeIp);
    return wasRemoved;
  }

  public void setOpenDomains(List<String> aopenDomains){
    openDomains = aopenDomains;
  }

  public boolean addOpenDomain(String aOpenDomain)
  {
    boolean wasAdded = false;
    wasAdded = openDomains.add(aOpenDomain);
    return wasAdded;
  }

  public boolean removeOpenDomain(String aOpenDomain)
  {
    boolean wasRemoved = false;
    wasRemoved = openDomains.remove(aOpenDomain);
    return wasRemoved;
  }

  public boolean setBudgetType(Integer aBudgetType)
  {
    boolean wasSet = false;
    budgetType = aBudgetType;
    wasSet = true;
    return wasSet;
  }

  public boolean setRegDomain(String aRegDomain)
  {
    boolean wasSet = false;
    regDomain = aRegDomain;
    wasSet = true;
    return wasSet;
  }

  public void setWeeklyBudget(List<Double> aweeklyBudget){
    weeklyBudget = aweeklyBudget;
  }

  public boolean addWeeklyBudget(Double aWeeklyBudget)
  {
    boolean wasAdded = false;
    wasAdded = weeklyBudget.add(aWeeklyBudget);
    return wasAdded;
  }

  public boolean removeWeeklyBudget(Double aWeeklyBudget)
  {
    boolean wasRemoved = false;
    wasRemoved = weeklyBudget.remove(aWeeklyBudget);
    return wasRemoved;
  }

  public boolean setUserStat(Integer aUserStat)
  {
    boolean wasSet = false;
    userStat = aUserStat;
    wasSet = true;
    return wasSet;
  }

  public void setBudgetOfflineTime(List<OfflineTimeType> abudgetOfflineTime){
    budgetOfflineTime = abudgetOfflineTime;
  }

  public boolean addBudgetOfflineTime(OfflineTimeType aBudgetOfflineTime)
  {
    boolean wasAdded = false;
    wasAdded = budgetOfflineTime.add(aBudgetOfflineTime);
    return wasAdded;
  }

  public boolean removeBudgetOfflineTime(OfflineTimeType aBudgetOfflineTime)
  {
    boolean wasRemoved = false;
    wasRemoved = budgetOfflineTime.remove(aBudgetOfflineTime);
    return wasRemoved;
  }

  public boolean setIsDynamicCreative(Boolean aIsDynamicCreative)
  {
    boolean wasSet = false;
    isDynamicCreative = aIsDynamicCreative;
    wasSet = true;
    return wasSet;
  }

  public boolean setDynamicCreativeParam(String aDynamicCreativeParam)
  {
    boolean wasSet = false;
    dynamicCreativeParam = aDynamicCreativeParam;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsDynamicTagSublink(Boolean aIsDynamicTagSublink)
  {
    boolean wasSet = false;
    isDynamicTagSublink = aIsDynamicTagSublink;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsDynamicTitle(Boolean aIsDynamicTitle)
  {
    boolean wasSet = false;
    isDynamicTitle = aIsDynamicTitle;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsDynamicHotRedirect(Boolean aIsDynamicHotRedirect)
  {
    boolean wasSet = false;
    isDynamicHotRedirect = aIsDynamicHotRedirect;
    wasSet = true;
    return wasSet;
  }

  public Long getUserId()
  {
    return userId;
  }

  public Double getBalance()
  {
    return balance;
  }

  public Double getCost()
  {
    return cost;
  }

  public Double getPayment()
  {
    return payment;
  }

  public Double getBudget()
  {
    return budget;
  }

  public Integer getRegionTarget(int index)
  {
    Integer aRegionTarget = regionTarget.get(index);
    return aRegionTarget;
  }

  public List<Integer> getRegionTarget()
  {
    return regionTarget;
  }

  public int numberOfRegionTarget()
  {
    int number = regionTarget.size();
    return number;
  }

  public boolean hasRegionTarget()
  {
    boolean has = regionTarget.size() > 0;
    return has;
  }

  public int indexOfRegionTarget(Integer aRegionTarget)
  {
    int index = regionTarget.indexOf(aRegionTarget);
    return index;
  }

  public String getExcludeIp(int index)
  {
    String aExcludeIp = excludeIp.get(index);
    return aExcludeIp;
  }

  public List<String> getExcludeIp()
  {
    return excludeIp;
  }

  public int numberOfExcludeIp()
  {
    int number = excludeIp.size();
    return number;
  }

  public boolean hasExcludeIp()
  {
    boolean has = excludeIp.size() > 0;
    return has;
  }

  public int indexOfExcludeIp(String aExcludeIp)
  {
    int index = excludeIp.indexOf(aExcludeIp);
    return index;
  }

  public String getOpenDomain(int index)
  {
    String aOpenDomain = openDomains.get(index);
    return aOpenDomain;
  }

  public List<String> getOpenDomains()
  {
    return openDomains;
  }

  public int numberOfOpenDomains()
  {
    int number = openDomains.size();
    return number;
  }

  public boolean hasOpenDomains()
  {
    boolean has = openDomains.size() > 0;
    return has;
  }

  public int indexOfOpenDomain(String aOpenDomain)
  {
    int index = openDomains.indexOf(aOpenDomain);
    return index;
  }

  public Integer getBudgetType()
  {
    return budgetType;
  }

  public String getRegDomain()
  {
    return regDomain;
  }

  public Double getWeeklyBudget(int index)
  {
    Double aWeeklyBudget = weeklyBudget.get(index);
    return aWeeklyBudget;
  }

  public List<Double> getWeeklyBudget()
  {
    return weeklyBudget;
  }

  public int numberOfWeeklyBudget()
  {
    int number = weeklyBudget.size();
    return number;
  }

  public boolean hasWeeklyBudget()
  {
    boolean has = weeklyBudget.size() > 0;
    return has;
  }

  public int indexOfWeeklyBudget(Double aWeeklyBudget)
  {
    int index = weeklyBudget.indexOf(aWeeklyBudget);
    return index;
  }

  public Integer getUserStat()
  {
    return userStat;
  }

  public OfflineTimeType getBudgetOfflineTime(int index)
  {
    OfflineTimeType aBudgetOfflineTime = budgetOfflineTime.get(index);
    return aBudgetOfflineTime;
  }

  public List<OfflineTimeType> getBudgetOfflineTime()
  {
    return budgetOfflineTime;
  }

  public int numberOfBudgetOfflineTime()
  {
    int number = budgetOfflineTime.size();
    return number;
  }

  public boolean hasBudgetOfflineTime()
  {
    boolean has = budgetOfflineTime.size() > 0;
    return has;
  }

  public int indexOfBudgetOfflineTime(OfflineTimeType aBudgetOfflineTime)
  {
    int index = budgetOfflineTime.indexOf(aBudgetOfflineTime);
    return index;
  }

  public Boolean getIsDynamicCreative()
  {
    return isDynamicCreative;
  }

  public String getDynamicCreativeParam()
  {
    return dynamicCreativeParam;
  }

  public Boolean getIsDynamicTagSublink()
  {
    return isDynamicTagSublink;
  }

  public Boolean getIsDynamicTitle()
  {
    return isDynamicTitle;
  }

  public Boolean getIsDynamicHotRedirect()
  {
    return isDynamicHotRedirect;
  }





  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "userId" + ":" + getUserId()+ "," +
            "balance" + ":" + getBalance()+ "," +
            "cost" + ":" + getCost()+ "," +
            "payment" + ":" + getPayment()+ "," +
            "budget" + ":" + getBudget()+ "," +
            "budgetType" + ":" + getBudgetType()+ "," +
            "regDomain" + ":" + getRegDomain()+ "," +
            "userStat" + ":" + getUserStat()+ "," +
            "isDynamicCreative" + ":" + getIsDynamicCreative()+ "," +
            "dynamicCreativeParam" + ":" + getDynamicCreativeParam()+ "," +
            "isDynamicTagSublink" + ":" + getIsDynamicTagSublink()+ "," +
            "isDynamicTitle" + ":" + getIsDynamicTitle()+ "," +
            "isDynamicHotRedirect" + ":" + getIsDynamicHotRedirect()+ "]"
     + outputString;
  }
}