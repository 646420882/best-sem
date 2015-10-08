/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.*;

// line 64 "../../../../../../../SDK.ump"
public class GetCreativeRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetCreativeRequest Attributes
  private List<String> creativeFields;
  private List<Long> ids;
  private Integer idType;
  private Integer getTemp;

  //------------------------
  // INTERFACE
  //------------------------

  public void setCreativeFields(List<String> acreativeFields){
    creativeFields = acreativeFields;
  }

  public boolean addCreativeField(String aCreativeField)
  {
    boolean wasAdded = false;
    wasAdded = creativeFields.add(aCreativeField);
    return wasAdded;
  }

  public boolean removeCreativeField(String aCreativeField)
  {
    boolean wasRemoved = false;
    wasRemoved = creativeFields.remove(aCreativeField);
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

  public boolean setGetTemp(Integer aGetTemp)
  {
    boolean wasSet = false;
    getTemp = aGetTemp;
    wasSet = true;
    return wasSet;
  }

  public String getCreativeField(int index)
  {
    String aCreativeField = creativeFields.get(index);
    return aCreativeField;
  }

  public List<String> getCreativeFields()
  {
    return creativeFields;
  }

  public int numberOfCreativeFields()
  {
    int number = creativeFields.size();
    return number;
  }

  public boolean hasCreativeFields()
  {
    boolean has = creativeFields.size() > 0;
    return has;
  }

  public int indexOfCreativeField(String aCreativeField)
  {
    int index = creativeFields.indexOf(aCreativeField);
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

  public Integer getGetTemp()
  {
    return getTemp;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "idType" + ":" + getIdType()+ "," +
            "getTemp" + ":" + getGetTemp()+ "]"
     + outputString;
  }
}