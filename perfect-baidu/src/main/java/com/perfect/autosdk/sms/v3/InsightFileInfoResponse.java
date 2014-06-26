/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

// line 14 "../../../../../../../SDKDemo.ump"
public class InsightFileInfoResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //InsightFileInfoResponse Attributes
  private String fileURL;
  private String MD5;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setFileURL(String aFileURL)
  {
    boolean wasSet = false;
    fileURL = aFileURL;
    wasSet = true;
    return wasSet;
  }

  public boolean setMD5(String aMD5)
  {
    boolean wasSet = false;
    MD5 = aMD5;
    wasSet = true;
    return wasSet;
  }

  public String getFileURL()
  {
    return fileURL;
  }

  public String getMD5()
  {
    return MD5;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "fileURL" + ":" + getFileURL()+ "," +
            "MD5" + ":" + getMD5()+ "]"
     + outputString;
  }
}