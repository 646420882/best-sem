/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 42 "../../../../../../../SDKDemo.ump"
public class GroupKeyword
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GroupKeyword Attributes
  private Long adgroupId;
  private List<KeywordType> keywordTypes;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAdgroupId(Long aAdgroupId)
  {
    boolean wasSet = false;
    adgroupId = aAdgroupId;
    wasSet = true;
    return wasSet;
  }

  public void setKeywordTypes(List<KeywordType> akeywordTypes){
    keywordTypes = akeywordTypes;
  }

  public boolean addKeywordType(KeywordType aKeywordType)
  {
    boolean wasAdded = false;
    wasAdded = keywordTypes.add(aKeywordType);
    return wasAdded;
  }

  public boolean removeKeywordType(KeywordType aKeywordType)
  {
    boolean wasRemoved = false;
    wasRemoved = keywordTypes.remove(aKeywordType);
    return wasRemoved;
  }

  public Long getAdgroupId()
  {
    return adgroupId;
  }

  public KeywordType getKeywordType(int index)
  {
    KeywordType aKeywordType = keywordTypes.get(index);
    return aKeywordType;
  }

  public List<KeywordType> getKeywordTypes()
  {
    return keywordTypes;
  }

  public int numberOfKeywordTypes()
  {
    int number = keywordTypes.size();
    return number;
  }

  public boolean hasKeywordTypes()
  {
    boolean has = keywordTypes.size() > 0;
    return has;
  }

  public int indexOfKeywordType(KeywordType aKeywordType)
  {
    int index = keywordTypes.indexOf(aKeywordType);
    return index;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "adgroupId" + ":" + getAdgroupId()+ "]"
     + outputString;
  }
}