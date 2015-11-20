/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;

// line 56 "../../../../../../../SDK.ump"
public class AddSublinkRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //AddSublinkRequest Attributes
  private List<SublinkType> sublinkTypes;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setSublinkTypes(List<SublinkType> aSublinkTypes)
  {
    boolean wasSet = false;
    sublinkTypes = aSublinkTypes;
    wasSet = true;
    return wasSet;
  }

  public List<SublinkType> getSublinkTypes()
  {
    return sublinkTypes;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "sublinkTypes" + "=" + (getSublinkTypes() != null ? !getSublinkTypes().equals(this)  ? getSublinkTypes().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}