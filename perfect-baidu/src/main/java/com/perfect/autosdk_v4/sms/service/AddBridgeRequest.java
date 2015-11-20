/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;

// line 86 "../../../../../../../SDK.ump"
public class AddBridgeRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //AddBridgeRequest Attributes
  private List<BridgeType> bridgeTypes;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setBridgeTypes(List<BridgeType> aBridgeTypes)
  {
    boolean wasSet = false;
    bridgeTypes = aBridgeTypes;
    wasSet = true;
    return wasSet;
  }

  public List<BridgeType> getBridgeTypes()
  {
    return bridgeTypes;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "bridgeTypes" + "=" + (getBridgeTypes() != null ? !getBridgeTypes().equals(this)  ? getBridgeTypes().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}