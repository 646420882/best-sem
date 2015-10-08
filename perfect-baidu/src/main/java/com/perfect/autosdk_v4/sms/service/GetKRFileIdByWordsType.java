/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;

// line 78 "../../../../../../../SDK.ump"
public class GetKRFileIdByWordsType
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetKRFileIdByWordsType Attributes
  private String fileId;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setFileId(String aFileId)
  {
    boolean wasSet = false;
    fileId = aFileId;
    wasSet = true;
    return wasSet;
  }

  public String getFileId()
  {
    return fileId;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "fileId" + ":" + getFileId()+ "]"
     + outputString;
  }
}