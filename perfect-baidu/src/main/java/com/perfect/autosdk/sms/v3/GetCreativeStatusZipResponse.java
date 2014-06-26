/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

// line 143 "../../../../../../../SDKDemo.ump"
public class GetCreativeStatusZipResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetCreativeStatusZipResponse Attributes
  private byte[] creativeStatus;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCreativeStatus(byte[] aCreativeStatus)
  {
    boolean wasSet = false;
    creativeStatus = aCreativeStatus;
    wasSet = true;
    return wasSet;
  }

  public byte[] getCreativeStatus()
  {
    return creativeStatus;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "creativeStatus" + "=" + (getCreativeStatus() != null ? !getCreativeStatus().equals(this)  ? getCreativeStatus().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}