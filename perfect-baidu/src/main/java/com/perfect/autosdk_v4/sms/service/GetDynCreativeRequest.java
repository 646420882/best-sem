/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.*;

// line 104 "../../../../../../../SDK.ump"
public class GetDynCreativeRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetDynCreativeRequest Attributes
  private List<String> dynCreativeFields;
  private List<Long> ids;
  private Integer idType;

  //------------------------
  // INTERFACE
  //------------------------

  public void setDynCreativeFields(List<String> adynCreativeFields){
    dynCreativeFields = adynCreativeFields;
  }

  public boolean addDynCreativeField(String aDynCreativeField)
  {
    boolean wasAdded = false;
    wasAdded = dynCreativeFields.add(aDynCreativeField);
    return wasAdded;
  }

  public boolean removeDynCreativeField(String aDynCreativeField)
  {
    boolean wasRemoved = false;
    wasRemoved = dynCreativeFields.remove(aDynCreativeField);
    return wasRemoved;
  }

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

  public String getDynCreativeField(int index)
  {
    String aDynCreativeField = dynCreativeFields.get(index);
    return aDynCreativeField;
  }

  public List<String> getDynCreativeFields()
  {
    return dynCreativeFields;
  }

  public int numberOfDynCreativeFields()
  {
    int number = dynCreativeFields.size();
    return number;
  }

  public boolean hasDynCreativeFields()
  {
    boolean has = dynCreativeFields.size() > 0;
    return has;
  }

  public int indexOfDynCreativeField(String aDynCreativeField)
  {
    int index = dynCreativeFields.indexOf(aDynCreativeField);
    return index;
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