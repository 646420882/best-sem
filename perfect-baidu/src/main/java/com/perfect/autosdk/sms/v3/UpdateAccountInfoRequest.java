/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

// line 46 "../../../../../../../SDKDemo.ump"
public class UpdateAccountInfoRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //UpdateAccountInfoRequest Attributes
  private AccountInfoType accountInfoType;
  private Integer extended;

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

  public boolean setExtended(Integer aExtended)
  {
    boolean wasSet = false;
    extended = aExtended;
    wasSet = true;
    return wasSet;
  }

  public AccountInfoType getAccountInfoType()
  {
    return accountInfoType;
  }

  public Integer getExtended()
  {
    return extended;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "extended" + ":" + getExtended()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "accountInfoType" + "=" + (getAccountInfoType() != null ? !getAccountInfoType().equals(this)  ? getAccountInfoType().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}