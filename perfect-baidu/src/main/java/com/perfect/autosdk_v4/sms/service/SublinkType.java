/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.*;

// line 104 "../../../../../../../SDK.ump"
public class SublinkType
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //SublinkType Attributes
  private Long sublinkId;
  private List<SublinkInfo> sublinkInfos;
  private Long adgroupId;
  private Boolean pause;
  private Integer status;
  private Integer device;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setSublinkId(Long aSublinkId)
  {
    boolean wasSet = false;
    sublinkId = aSublinkId;
    wasSet = true;
    return wasSet;
  }

  public void setSublinkInfos(List<SublinkInfo> asublinkInfos){
    sublinkInfos = asublinkInfos;
  }

  public boolean addSublinkInfo(SublinkInfo aSublinkInfo)
  {
    boolean wasAdded = false;
    wasAdded = sublinkInfos.add(aSublinkInfo);
    return wasAdded;
  }

  public boolean removeSublinkInfo(SublinkInfo aSublinkInfo)
  {
    boolean wasRemoved = false;
    wasRemoved = sublinkInfos.remove(aSublinkInfo);
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

  public boolean setDevice(Integer aDevice)
  {
    boolean wasSet = false;
    device = aDevice;
    wasSet = true;
    return wasSet;
  }

  public Long getSublinkId()
  {
    return sublinkId;
  }

  public SublinkInfo getSublinkInfo(int index)
  {
    SublinkInfo aSublinkInfo = sublinkInfos.get(index);
    return aSublinkInfo;
  }

  public List<SublinkInfo> getSublinkInfos()
  {
    return sublinkInfos;
  }

  public int numberOfSublinkInfos()
  {
    int number = sublinkInfos.size();
    return number;
  }

  public boolean hasSublinkInfos()
  {
    boolean has = sublinkInfos.size() > 0;
    return has;
  }

  public int indexOfSublinkInfo(SublinkInfo aSublinkInfo)
  {
    int index = sublinkInfos.indexOf(aSublinkInfo);
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

  public Integer getDevice()
  {
    return device;
  }


  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "sublinkId" + ":" + getSublinkId()+ "," +
            "adgroupId" + ":" + getAdgroupId()+ "," +
            "pause" + ":" + getPause()+ "," +
            "status" + ":" + getStatus()+ "," +
            "device" + ":" + getDevice()+ "]"
     + outputString;
  }
}