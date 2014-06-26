/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

// line 158 "../../../../../../../SDKDemo.ump"
public class GetKeywordStatusZipResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetKeywordStatusZipResponse Attributes
  private byte[] keywordStatus;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setKeywordStatus(byte[] aKeywordStatus)
  {
    boolean wasSet = false;
    keywordStatus = aKeywordStatus;
    wasSet = true;
    return wasSet;
  }

  public byte[] getKeywordStatus()
  {
    return keywordStatus;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "keywordStatus" + "=" + (getKeywordStatus() != null ? !getKeywordStatus().equals(this)  ? getKeywordStatus().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}