/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

// line 116 "../../../../../../../SDKDemo.ump"
public class GetKRFilePathResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetKRFilePathResponse Attributes
  private String filePath;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setFilePath(String aFilePath)
  {
    boolean wasSet = false;
    filePath = aFilePath;
    wasSet = true;
    return wasSet;
  }

  public String getFilePath()
  {
    return filePath;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "filePath" + ":" + getFilePath()+ "]"
     + outputString;
  }
}