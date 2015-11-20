/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;

// line 46 "../../../../../../../SDK.ump"
public class UpdateAccountInfoRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //UpdateAccountInfoRequest Attributes
  private AccountInfo accountInfo;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAccountInfo(AccountInfo aAccountInfo)
  {
    boolean wasSet = false;
    accountInfo = aAccountInfo;
    wasSet = true;
    return wasSet;
  }

  public AccountInfo getAccountInfo()
  {
    return accountInfo;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "accountInfo" + "=" + (getAccountInfo() != null ? !getAccountInfo().equals(this)  ? getAccountInfo().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}