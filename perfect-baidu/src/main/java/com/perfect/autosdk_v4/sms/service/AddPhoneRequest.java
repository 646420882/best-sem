/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;

// line 18 "../../../../../../../SDK.ump"
public class AddPhoneRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //AddPhoneRequest Attributes
  private List<PhoneType> phoneTypes;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPhoneTypes(List<PhoneType> aPhoneTypes)
  {
    boolean wasSet = false;
    phoneTypes = aPhoneTypes;
    wasSet = true;
    return wasSet;
  }

  public List<PhoneType> getPhoneTypes()
  {
    return phoneTypes;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "phoneTypes" + "=" + (getPhoneTypes() != null ? !getPhoneTypes().equals(this)  ? getPhoneTypes().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}