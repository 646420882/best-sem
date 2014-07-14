/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;

// line 95 "../../../../../../../SDKDemo.ump"
public class GetRealTimeDataRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetRealTimeDataRequest Attributes
  private RealTimeRequestType realTimeRequestTypes;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRealTimeRequestTypes(RealTimeRequestType aRealTimeRequestTypes)
  {
    boolean wasSet = false;
    realTimeRequestTypes = aRealTimeRequestTypes;
    wasSet = true;
    return wasSet;
  }

  public RealTimeRequestType getRealTimeRequestTypes()
  {
    return realTimeRequestTypes;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "realTimeRequestTypes" + "=" + (getRealTimeRequestTypes() != null ? !getRealTimeRequestTypes().equals(this)  ? getRealTimeRequestTypes().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}