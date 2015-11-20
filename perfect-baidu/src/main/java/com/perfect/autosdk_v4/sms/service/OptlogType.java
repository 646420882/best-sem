/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.Date;

// line 9 "../../../../../../../SDK.ump"
public class OptlogType
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //OptlogType Attributes
  private Long userId;
  private Long planId;
  private Long unitId;
  private Date optTime;
  private String optContent;
  private Integer optType;
  private Integer optLevel;
  private String oldValue;
  private String newValue;
  private String optObj;

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

  public boolean setPlanId(Long aPlanId)
  {
    boolean wasSet = false;
    planId = aPlanId;
    wasSet = true;
    return wasSet;
  }

  public boolean setUnitId(Long aUnitId)
  {
    boolean wasSet = false;
    unitId = aUnitId;
    wasSet = true;
    return wasSet;
  }

  public boolean setOptTime(Date aOptTime)
  {
    boolean wasSet = false;
    optTime = aOptTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setOptContent(String aOptContent)
  {
    boolean wasSet = false;
    optContent = aOptContent;
    wasSet = true;
    return wasSet;
  }

  public boolean setOptType(Integer aOptType)
  {
    boolean wasSet = false;
    optType = aOptType;
    wasSet = true;
    return wasSet;
  }

  public boolean setOptLevel(Integer aOptLevel)
  {
    boolean wasSet = false;
    optLevel = aOptLevel;
    wasSet = true;
    return wasSet;
  }

  public boolean setOldValue(String aOldValue)
  {
    boolean wasSet = false;
    oldValue = aOldValue;
    wasSet = true;
    return wasSet;
  }

  public boolean setNewValue(String aNewValue)
  {
    boolean wasSet = false;
    newValue = aNewValue;
    wasSet = true;
    return wasSet;
  }

  public boolean setOptObj(String aOptObj)
  {
    boolean wasSet = false;
    optObj = aOptObj;
    wasSet = true;
    return wasSet;
  }

  public Long getUserId()
  {
    return userId;
  }

  public Long getPlanId()
  {
    return planId;
  }

  public Long getUnitId()
  {
    return unitId;
  }

  public Date getOptTime()
  {
    return optTime;
  }

  public String getOptContent()
  {
    return optContent;
  }

  public Integer getOptType()
  {
    return optType;
  }

  public Integer getOptLevel()
  {
    return optLevel;
  }

  public String getOldValue()
  {
    return oldValue;
  }

  public String getNewValue()
  {
    return newValue;
  }

  public String getOptObj()
  {
    return optObj;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "userId" + ":" + getUserId()+ "," +
            "planId" + ":" + getPlanId()+ "," +
            "unitId" + ":" + getUnitId()+ "," +
            "optContent" + ":" + getOptContent()+ "," +
            "optType" + ":" + getOptType()+ "," +
            "optLevel" + ":" + getOptLevel()+ "," +
            "oldValue" + ":" + getOldValue()+ "," +
            "newValue" + ":" + getNewValue()+ "," +
            "optObj" + ":" + getOptObj()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "optTime" + "=" + (getOptTime() != null ? !getOptTime().equals(this)  ? getOptTime().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}