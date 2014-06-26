/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

// line 100 "../../../../../../../SDKDemo.ump"
public class GetRealTimePairDataRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetRealTimePairDataRequest Attributes
  private RealTimeRequestType realTimePairRequestTypes;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRealTimePairRequestTypes(RealTimeRequestType aRealTimePairRequestTypes)
  {
    boolean wasSet = false;
    realTimePairRequestTypes = aRealTimePairRequestTypes;
    wasSet = true;
    return wasSet;
  }

  public RealTimeRequestType getRealTimePairRequestTypes()
  {
    return realTimePairRequestTypes;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "realTimePairRequestTypes" + "=" + (getRealTimePairRequestTypes() != null ? !getRealTimePairRequestTypes().equals(this)  ? getRealTimePairRequestTypes().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}