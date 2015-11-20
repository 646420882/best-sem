/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.*;

// line 115 "../../../../../../../SDK.ump"
public class GetSublinkBySublinkIdRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetSublinkBySublinkIdRequest Attributes
  private List<Long> ids;
  private Integer idType;
  private List<String> sublinkFields;
  private Integer getTemp;
  private Integer device;

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

  public void setSublinkFields(List<String> asublinkFields){
    sublinkFields = asublinkFields;
  }

  public boolean addSublinkField(String aSublinkField)
  {
    boolean wasAdded = false;
    wasAdded = sublinkFields.add(aSublinkField);
    return wasAdded;
  }

  public boolean removeSublinkField(String aSublinkField)
  {
    boolean wasRemoved = false;
    wasRemoved = sublinkFields.remove(aSublinkField);
    return wasRemoved;
  }

  public boolean setGetTemp(Integer aGetTemp)
  {
    boolean wasSet = false;
    getTemp = aGetTemp;
    wasSet = true;
    return wasSet;
  }

  public boolean setDevice(Integer aDevice)
  {
    boolean wasSet = false;
    device = aDevice;
    wasSet = true;
    return wasSet;
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

  public String getSublinkField(int index)
  {
    String aSublinkField = sublinkFields.get(index);
    return aSublinkField;
  }

  public List<String> getSublinkFields()
  {
    return sublinkFields;
  }

  public int numberOfSublinkFields()
  {
    int number = sublinkFields.size();
    return number;
  }

  public boolean hasSublinkFields()
  {
    boolean has = sublinkFields.size() > 0;
    return has;
  }

  public int indexOfSublinkField(String aSublinkField)
  {
    int index = sublinkFields.indexOf(aSublinkField);
    return index;
  }

  public Integer getGetTemp()
  {
    return getTemp;
  }

  public Integer getDevice()
  {
    return device;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "idType" + ":" + getIdType()+ "," +
            "getTemp" + ":" + getGetTemp()+ "," +
            "device" + ":" + getDevice()+ "]"
     + outputString;
  }
}