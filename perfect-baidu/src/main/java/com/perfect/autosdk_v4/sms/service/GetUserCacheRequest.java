/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;

// line 262 "../../../../../../../SDK.ump"
public class GetUserCacheRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetUserCacheRequest Attributes
  private String action;
  private String biz;
  private String fileId;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAction(String aAction)
  {
    boolean wasSet = false;
    action = aAction;
    wasSet = true;
    return wasSet;
  }

  public boolean setBiz(String aBiz)
  {
    boolean wasSet = false;
    biz = aBiz;
    wasSet = true;
    return wasSet;
  }

  public boolean setFileId(String aFileId)
  {
    boolean wasSet = false;
    fileId = aFileId;
    wasSet = true;
    return wasSet;
  }

  public String getAction()
  {
    return action;
  }

  public String getBiz()
  {
    return biz;
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
            "action" + ":" + getAction()+ "," +
            "biz" + ":" + getBiz()+ "," +
            "fileId" + ":" + getFileId()+ "]"
     + outputString;
  }
}