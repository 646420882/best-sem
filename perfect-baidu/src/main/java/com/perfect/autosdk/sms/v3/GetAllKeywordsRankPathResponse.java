/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;

// line 35 "../../../../../../../SDKDemo.ump"
public class GetAllKeywordsRankPathResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetAllKeywordsRankPathResponse Attributes
  private String keywordsRankFilePath;
  private String keywordsRankFileMd5;
  private String date;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setKeywordsRankFilePath(String aKeywordsRankFilePath)
  {
    boolean wasSet = false;
    keywordsRankFilePath = aKeywordsRankFilePath;
    wasSet = true;
    return wasSet;
  }

  public boolean setKeywordsRankFileMd5(String aKeywordsRankFileMd5)
  {
    boolean wasSet = false;
    keywordsRankFileMd5 = aKeywordsRankFileMd5;
    wasSet = true;
    return wasSet;
  }

  public boolean setDate(String aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public String getKeywordsRankFilePath()
  {
    return keywordsRankFilePath;
  }

  public String getKeywordsRankFileMd5()
  {
    return keywordsRankFileMd5;
  }

  public String getDate()
  {
    return date;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "keywordsRankFilePath" + ":" + getKeywordsRankFilePath()+ "," +
            "keywordsRankFileMd5" + ":" + getKeywordsRankFileMd5()+ "," +
            "date" + ":" + getDate()+ "]"
     + outputString;
  }
}