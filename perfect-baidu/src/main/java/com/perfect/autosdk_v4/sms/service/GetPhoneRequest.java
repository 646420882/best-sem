/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.*;

// line 166 "../../../../../../../SDK.ump"
public class GetPhoneRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetPhoneRequest Attributes
  private List<Long> ids;
  private Integer idType;
  private List<String> phoneFields;

  //------------------------
  // INTERFACE
  //------------------------

  public void setIds(List<Long> aids){
    ids = aids;
  }

  public boolean addId(Long aId)
  {
    boolean wasAdded = false;
    wasAdded = ids.add(aId);
    return wasAdded;
  }

  public boolean removeId(Long aId)
  {
    boolean wasRemoved = false;
    wasRemoved = ids.remove(aId);
    return wasRemoved;
  }

  public boolean setIdType(Integer aIdType)
  {
    boolean wasSet = false;
    idType = aIdType;
    wasSet = true;
    return wasSet;
  }

  public void setPhoneFields(List<String> aphoneFields){
    phoneFields = aphoneFields;
  }

  public boolean addPhoneField(String aPhoneField)
  {
    boolean wasAdded = false;
    wasAdded = phoneFields.add(aPhoneField);
    return wasAdded;
  }

  public boolean removePhoneField(String aPhoneField)
  {
    boolean wasRemoved = false;
    wasRemoved = phoneFields.remove(aPhoneField);
    return wasRemoved;
  }

  public Long getId(int index)
  {
    Long aId = ids.get(index);
    return aId;
  }

  public List<Long> getIds()
  {
    return ids;
  }

  public int numberOfIds()
  {
    int number = ids.size();
    return number;
  }

  public boolean hasIds()
  {
    boolean has = ids.size() > 0;
    return has;
  }

  public int indexOfId(Long aId)
  {
    int index = ids.indexOf(aId);
    return index;
  }

  public Integer getIdType()
  {
    return idType;
  }

  public String getPhoneField(int index)
  {
    String aPhoneField = phoneFields.get(index);
    return aPhoneField;
  }

  public List<String> getPhoneFields()
  {
    return phoneFields;
  }

  public int numberOfPhoneFields()
  {
    int number = phoneFields.size();
    return number;
  }

  public boolean hasPhoneFields()
  {
    boolean has = phoneFields.size() > 0;
    return has;
  }

  public int indexOfPhoneField(String aPhoneField)
  {
    int index = phoneFields.indexOf(aPhoneField);
    return index;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "idType" + ":" + getIdType()+ "]"
     + outputString;
  }
}