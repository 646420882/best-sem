/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

// line 23 "../../../../../../../SDKDemo.ump"
public class PreviewInfo
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PreviewInfo Attributes
  private String keyword;
  private String data;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setKeyword(String aKeyword)
  {
    boolean wasSet = false;
    keyword = aKeyword;
    wasSet = true;
    return wasSet;
  }

  public boolean setData(String aData)
  {
    boolean wasSet = false;
    data = aData;
    wasSet = true;
    return wasSet;
  }

  public String getKeyword()
  {
    return keyword;
  }

  public String getData()
  {
    return data;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "keyword" + ":" + getKeyword()+ "," +
            "data" + ":" + getData()+ "]"
     + outputString;
  }
}