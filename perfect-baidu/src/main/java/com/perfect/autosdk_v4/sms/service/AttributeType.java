/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;

// line 50 "../../../../../../../SDK.ump"
public class AttributeType
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //AttributeType Attributes
  private String key;
  private List<Integer> value;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setKey(String aKey)
  {
    boolean wasSet = false;
    key = aKey;
    wasSet = true;
    return wasSet;
  }

  public boolean setValue(List<Integer> aValue)
  {
    boolean wasSet = false;
    value = aValue;
    wasSet = true;
    return wasSet;
  }

  public String getKey()
  {
    return key;
  }

  public List<Integer> getValue()
  {
    return value;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "key" + ":" + getKey()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "value" + "=" + (getValue() != null ? !getValue().equals(this)  ? getValue().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}