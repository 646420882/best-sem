/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.*;

// line 52 "../../../../../../../SDK.ump"
public class GetAccountInfoRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetAccountInfoRequest Attributes
  private List<String> accountFields;

  //------------------------
  // INTERFACE
  //------------------------

  public void setAccountFields(List<String> aaccountFields){
    accountFields = aaccountFields;
  }

  public boolean addAccountField(String aAccountField)
  {
    boolean wasAdded = false;
    wasAdded = accountFields.add(aAccountField);
    return wasAdded;
  }

  public boolean removeAccountField(String aAccountField)
  {
    boolean wasRemoved = false;
    wasRemoved = accountFields.remove(aAccountField);
    return wasRemoved;
  }

  public String getAccountField(int index)
  {
    String aAccountField = accountFields.get(index);
    return aAccountField;
  }

  public List<String> getAccountFields()
  {
    return accountFields;
  }

  public int numberOfAccountFields()
  {
    int number = accountFields.size();
    return number;
  }

  public boolean hasAccountFields()
  {
    boolean has = accountFields.size() > 0;
    return has;
  }

  public int indexOfAccountField(String aAccountField)
  {
    int index = accountFields.indexOf(aAccountField);
    return index;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+ "]"
     + outputString;
  }
}