/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.*;

// line 12 "../../../../../../../SDK.ump"
public class GetAdgroupByAdgroupIdRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetAdgroupByAdgroupIdRequest Attributes
  private List<String> adgroupFields;
  private List<Long> ids;
  private Integer idType;

  //------------------------
  // INTERFACE
  //------------------------

  public void setAdgroupFields(List<String> aadgroupFields){
    adgroupFields = aadgroupFields;
  }

  public boolean addAdgroupField(String aAdgroupField)
  {
    boolean wasAdded = false;
    wasAdded = adgroupFields.add(aAdgroupField);
    return wasAdded;
  }

  public boolean removeAdgroupField(String aAdgroupField)
  {
    boolean wasRemoved = false;
    wasRemoved = adgroupFields.remove(aAdgroupField);
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

  public String getAdgroupField(int index)
  {
    String aAdgroupField = adgroupFields.get(index);
    return aAdgroupField;
  }

  public List<String> getAdgroupFields()
  {
    return adgroupFields;
  }

  public int numberOfAdgroupFields()
  {
    int number = adgroupFields.size();
    return number;
  }

  public boolean hasAdgroupFields()
  {
    boolean has = adgroupFields.size() > 0;
    return has;
  }

  public int indexOfAdgroupField(String aAdgroupField)
  {
    int index = adgroupFields.indexOf(aAdgroupField);
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