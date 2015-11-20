/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;
import java.util.*;

// line 36 "../../../../../../../SDK.ump"
public class GetWordRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetWordRequest Attributes
  private List<String> wordFields;
  private List<Long> ids;
  private Integer idType;
  private Integer getTemp;

  //------------------------
  // INTERFACE
  //------------------------

  public void setWordFields(List<String> awordFields){
    wordFields = awordFields;
  }

  public boolean addWordField(String aWordField)
  {
    boolean wasAdded = false;
    wasAdded = wordFields.add(aWordField);
    return wasAdded;
  }

  public boolean removeWordField(String aWordField)
  {
    boolean wasRemoved = false;
    wasRemoved = wordFields.remove(aWordField);
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

  public String getWordField(int index)
  {
    String aWordField = wordFields.get(index);
    return aWordField;
  }

  public List<String> getWordFields()
  {
    return wordFields;
  }

  public int numberOfWordFields()
  {
    int number = wordFields.size();
    return number;
  }

  public boolean hasWordFields()
  {
    boolean has = wordFields.size() > 0;
    return has;
  }

  public int indexOfWordField(String aWordField)
  {
    int index = wordFields.indexOf(aWordField);
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