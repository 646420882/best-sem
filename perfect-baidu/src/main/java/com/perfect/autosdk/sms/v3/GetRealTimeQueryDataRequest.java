/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;

// line 105 "../../../../../../../SDKDemo.ump"
public class GetRealTimeQueryDataRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetRealTimeQueryDataRequest Attributes
  private RealTimeQueryRequestType realTimeQueryRequestTypes;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRealTimeQueryRequestTypes(RealTimeQueryRequestType aRealTimeQueryRequestTypes)
  {
    boolean wasSet = false;
    realTimeQueryRequestTypes = aRealTimeQueryRequestTypes;
    wasSet = true;
    return wasSet;
  }

  public RealTimeQueryRequestType getRealTimeQueryRequestTypes()
  {
    return realTimeQueryRequestTypes;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "realTimeQueryRequestTypes" + "=" + (getRealTimeQueryRequestTypes() != null ? !getRealTimeQueryRequestTypes().equals(this)  ? getRealTimeQueryRequestTypes().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}