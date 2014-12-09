/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;

// line 78 "../../../../../../../SDKDemo.ump"
public class VariableColumn
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //VariableColumn Attributes
  private String key;
  private String value;

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

  public boolean setValue(String aValue)
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

  public String getValue()
  {
    return value;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "key" + ":" + getKey()+ "," +
            "value" + ":" + getValue()+ "]"
     + outputString;
  }
}