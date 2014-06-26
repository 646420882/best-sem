/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.List;

// line 175 "../../../../../../../SDKDemo.ump"
public class AddPhoneResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //AddPhoneResponse Attributes
  private List<PhoneType> phoneTypes;

  //------------------------
  // INTERFACE
  //------------------------

  public void setPhoneTypes(List<PhoneType> aphoneTypes){
    phoneTypes = aphoneTypes;
  }

  public boolean addPhoneType(PhoneType aPhoneType)
  {
    boolean wasAdded = false;
    wasAdded = phoneTypes.add(aPhoneType);
    return wasAdded;
  }

  public boolean removePhoneType(PhoneType aPhoneType)
  {
    boolean wasRemoved = false;
    wasRemoved = phoneTypes.remove(aPhoneType);
    return wasRemoved;
  }

  public PhoneType getPhoneType(int index)
  {
    PhoneType aPhoneType = phoneTypes.get(index);
    return aPhoneType;
  }

  public List<PhoneType> getPhoneTypes()
  {
    return phoneTypes;
  }

  public int numberOfPhoneTypes()
  {
    int number = phoneTypes.size();
    return number;
  }

  public boolean hasPhoneTypes()
  {
    boolean has = phoneTypes.size() > 0;
    return has;
  }

  public int indexOfPhoneType(PhoneType aPhoneType)
  {
    int index = phoneTypes.indexOf(aPhoneType);
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