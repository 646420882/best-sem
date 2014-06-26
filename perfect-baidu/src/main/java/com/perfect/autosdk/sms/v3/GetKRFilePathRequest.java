/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

// line 111 "../../../../../../../SDKDemo.ump"
public class GetKRFilePathRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetKRFilePathRequest Attributes
  private String krFileId;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setKrFileId(String aKrFileId)
  {
    boolean wasSet = false;
    krFileId = aKrFileId;
    wasSet = true;
    return wasSet;
  }

  public String getKrFileId()
  {
    return krFileId;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "krFileId" + ":" + getKrFileId()+ "]"
     + outputString;
  }
}