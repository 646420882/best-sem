/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.*;

// line 48 "../../../../../../../SDK.ump"
public class GetBridgeRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetBridgeRequest Attributes
  private List<Long> ids;
  private Integer idType;
  private List<String> bridgeFields;

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

  public void setBridgeFields(List<String> abridgeFields){
    bridgeFields = abridgeFields;
  }

  public boolean addBridgeField(String aBridgeField)
  {
    boolean wasAdded = false;
    wasAdded = bridgeFields.add(aBridgeField);
    return wasAdded;
  }

  public boolean removeBridgeField(String aBridgeField)
  {
    boolean wasRemoved = false;
    wasRemoved = bridgeFields.remove(aBridgeField);
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

  public String getBridgeField(int index)
  {
    String aBridgeField = bridgeFields.get(index);
    return aBridgeField;
  }

  public List<String> getBridgeFields()
  {
    return bridgeFields;
  }

  public int numberOfBridgeFields()
  {
    int number = bridgeFields.size();
    return number;
  }

  public boolean hasBridgeFields()
  {
    boolean has = bridgeFields.size() > 0;
    return has;
  }

  public int indexOfBridgeField(String aBridgeField)
  {
    int index = bridgeFields.indexOf(aBridgeField);
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