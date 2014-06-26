/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

// line 9 "../../../../../../../SDKDemo.ump"
public class IPBindDTO
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //IPBindDTO Attributes
  private Long bindId;
  private Long targetId;
  private Integer targetType;
  private Integer isPause;
  private Integer isDel;
  private Long planid;
  private String planname;
  private Long unitid;
  private String unitname;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setBindId(Long aBindId)
  {
    boolean wasSet = false;
    bindId = aBindId;
    wasSet = true;
    return wasSet;
  }

  public boolean setTargetId(Long aTargetId)
  {
    boolean wasSet = false;
    targetId = aTargetId;
    wasSet = true;
    return wasSet;
  }

  public boolean setTargetType(Integer aTargetType)
  {
    boolean wasSet = false;
    targetType = aTargetType;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsPause(Integer aIsPause)
  {
    boolean wasSet = false;
    isPause = aIsPause;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsDel(Integer aIsDel)
  {
    boolean wasSet = false;
    isDel = aIsDel;
    wasSet = true;
    return wasSet;
  }

  public boolean setPlanid(Long aPlanid)
  {
    boolean wasSet = false;
    planid = aPlanid;
    wasSet = true;
    return wasSet;
  }

  public boolean setPlanname(String aPlanname)
  {
    boolean wasSet = false;
    planname = aPlanname;
    wasSet = true;
    return wasSet;
  }

  public boolean setUnitid(Long aUnitid)
  {
    boolean wasSet = false;
    unitid = aUnitid;
    wasSet = true;
    return wasSet;
  }

  public boolean setUnitname(String aUnitname)
  {
    boolean wasSet = false;
    unitname = aUnitname;
    wasSet = true;
    return wasSet;
  }

  public Long getBindId()
  {
    return bindId;
  }

  public Long getTargetId()
  {
    return targetId;
  }

  public Integer getTargetType()
  {
    return targetType;
  }

  public Integer getIsPause()
  {
    return isPause;
  }

  public Integer getIsDel()
  {
    return isDel;
  }

  public Long getPlanid()
  {
    return planid;
  }

  public String getPlanname()
  {
    return planname;
  }

  public Long getUnitid()
  {
    return unitid;
  }

  public String getUnitname()
  {
    return unitname;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "bindId" + ":" + getBindId()+ "," +
            "targetId" + ":" + getTargetId()+ "," +
            "targetType" + ":" + getTargetType()+ "," +
            "isPause" + ":" + getIsPause()+ "," +
            "isDel" + ":" + getIsDel()+ "," +
            "planid" + ":" + getPlanid()+ "," +
            "planname" + ":" + getPlanname()+ "," +
            "unitid" + ":" + getUnitid()+ "," +
            "unitname" + ":" + getUnitname()+ "]"
     + outputString;
  }
}