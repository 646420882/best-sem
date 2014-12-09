/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;

// line 9 "../../../../../../../SDKDemo.ump"
public class BidInsightFileInfo
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //BidInsightFileInfo Attributes
  private String fileURL;
  private String md5;

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

  public boolean setMd5(String aMd5)
  {
    boolean wasSet = false;
    md5 = aMd5;
    wasSet = true;
    return wasSet;
  }

  public String getFileURL()
  {
    return fileURL;
  }

  public String getMd5()
  {
    return md5;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "fileURL" + ":" + getFileURL()+ "," +
            "md5" + ":" + getMd5()+ "]"
     + outputString;
  }
}