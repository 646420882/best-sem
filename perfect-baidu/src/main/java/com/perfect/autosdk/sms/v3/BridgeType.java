/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;

// line 216 "../../../../../../../SDKDemo.ump"
public class BridgeType
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //BridgeType Attributes
  private Long bridgeId;
  private Long adgroupId;
  private Boolean pause;
  private Integer status;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setBridgeId(Long aBridgeId)
  {
    boolean wasSet = false;
    bridgeId = aBridgeId;
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

  public Long getBridgeId()
  {
    return bridgeId;
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
            "bridgeId" + ":" + getBridgeId()+ "," +
            "adgroupId" + ":" + getAdgroupId()+ "," +
            "pause" + ":" + getPause()+ "," +
            "status" + ":" + getStatus()+ "]"
     + outputString;
  }
}