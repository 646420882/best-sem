/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.List;

// line 104 "../../../../../../../SDKDemo.ump"
public class MobileSublinkType
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //MobileSublinkType Attributes
  private Long mobileSublinkId;
  private List<MobileSublinkInfo> mobileSublinkInfos;
  private Long adgroupId;
  private Boolean pause;
  private Integer status;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setMobileSublinkId(Long aMobileSublinkId)
  {
    boolean wasSet = false;
    mobileSublinkId = aMobileSublinkId;
    wasSet = true;
    return wasSet;
  }

  public void setMobileSublinkInfos(List<MobileSublinkInfo> amobileSublinkInfos){
    mobileSublinkInfos = amobileSublinkInfos;
  }

  public boolean addMobileSublinkInfo(MobileSublinkInfo aMobileSublinkInfo)
  {
    boolean wasAdded = false;
    wasAdded = mobileSublinkInfos.add(aMobileSublinkInfo);
    return wasAdded;
  }

  public boolean removeMobileSublinkInfo(MobileSublinkInfo aMobileSublinkInfo)
  {
    boolean wasRemoved = false;
    wasRemoved = mobileSublinkInfos.remove(aMobileSublinkInfo);
    return wasRemoved;
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

  public Long getMobileSublinkId()
  {
    return mobileSublinkId;
  }

  public MobileSublinkInfo getMobileSublinkInfo(int index)
  {
    MobileSublinkInfo aMobileSublinkInfo = mobileSublinkInfos.get(index);
    return aMobileSublinkInfo;
  }

  public List<MobileSublinkInfo> getMobileSublinkInfos()
  {
    return mobileSublinkInfos;
  }

  public int numberOfMobileSublinkInfos()
  {
    int number = mobileSublinkInfos.size();
    return number;
  }

  public boolean hasMobileSublinkInfos()
  {
    boolean has = mobileSublinkInfos.size() > 0;
    return has;
  }

  public int indexOfMobileSublinkInfo(MobileSublinkInfo aMobileSublinkInfo)
  {
    int index = mobileSublinkInfos.indexOf(aMobileSublinkInfo);
    return index;
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
            "mobileSublinkId" + ":" + getMobileSublinkId()+ "," +
            "adgroupId" + ":" + getAdgroupId()+ "," +
            "pause" + ":" + getPause()+ "," +
            "status" + ":" + getStatus()+ "]"
     + outputString;
  }
}