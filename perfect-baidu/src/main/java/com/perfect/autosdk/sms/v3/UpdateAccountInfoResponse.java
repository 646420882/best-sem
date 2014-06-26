/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

// line 52 "../../../../../../../SDKDemo.ump"
public class UpdateAccountInfoResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //UpdateAccountInfoResponse Attributes
  private AccountInfoType accountInfoType;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAccountInfoType(AccountInfoType aAccountInfoType)
  {
    boolean wasSet = false;
    accountInfoType = aAccountInfoType;
    wasSet = true;
    return wasSet;
  }

  public AccountInfoType getAccountInfoType()
  {
    return accountInfoType;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "accountInfoType" + "=" + (getAccountInfoType() != null ? !getAccountInfoType().equals(this)  ? getAccountInfoType().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}