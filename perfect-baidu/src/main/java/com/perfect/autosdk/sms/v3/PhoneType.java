/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

// line 161 "../../../../../../../SDKDemo.ump"
public class PhoneType
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PhoneType Attributes
  private Long phoneId;
  private String phoneNum;
  private Long adgroupId;
  private Boolean pause;
  private Integer status;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPhoneId(Long aPhoneId)
  {
    boolean wasSet = false;
    phoneId = aPhoneId;
    wasSet = true;
    return wasSet;
  }

  public boolean setPhoneNum(String aPhoneNum)
  {
    boolean wasSet = false;
    phoneNum = aPhoneNum;
    wasSet = true;
    return wasSet;
  }

  public boolean setAdgroupId(Long aAdgroupId)
  {
    boolean wasSet = false;
    adgroupId = aAdgroupId;
    wasSet = true;
    return wasSet;
  }

  public boolean setPause(Boolean aPause)
  {
    boolean wasSet = false;
    pause = aPause;
    wasSet = true;
    return wasSet;
  }

  public boolean setStatus(Integer aStatus)
  {
    boolean wasSet = false;
    status = aStatus;
    wasSet = true;
    return wasSet;
  }

  public Long getPhoneId()
  {
    return phoneId;
  }

  public String getPhoneNum()
  {
    return phoneNum;
  }

  public Long getAdgroupId()
  {
    return adgroupId;
  }

  public Boolean getPause()
  {
    return pause;
  }

  public Integer getStatus()
  {
    return status;
  }


  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "phoneId" + ":" + getPhoneId()+ "," +
            "phoneNum" + ":" + getPhoneNum()+ "," +
            "adgroupId" + ":" + getAdgroupId()+ "," +
            "pause" + ":" + getPause()+ "," +
            "status" + ":" + getStatus()+ "]"
     + outputString;
  }
}