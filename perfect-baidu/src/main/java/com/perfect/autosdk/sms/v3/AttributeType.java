/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 14 "../../../../../../../SDKDemo.ump"
public class AttributeType
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //AttributeType Attributes
  private String key;
  private List<Integer> value;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setKey(String aKey)
  {
    boolean wasSet = false;
    key = aKey;
    wasSet = true;
    return wasSet;
  }

  public void setValue(List<Integer> avalue){
    value = avalue;
  }

  public boolean addValue(Integer aValue)
  {
    boolean wasAdded = false;
    wasAdded = value.add(aValue);
    return wasAdded;
  }

  public boolean removeValue(Integer aValue)
  {
    boolean wasRemoved = false;
    wasRemoved = value.remove(aValue);
    return wasRemoved;
  }

  public String getKey()
  {
    return key;
  }

  public Integer getValue(int index)
  {
    Integer aValue = value.get(index);
    return aValue;
  }

  public List<Integer> getValue()
  {
    return value;
  }

  public int numberOfValue()
  {
    int number = value.size();
    return number;
  }

  public boolean hasValue()
  {
    boolean has = value.size() > 0;
    return has;
  }

  public int indexOfValue(Integer aValue)
  {
    int index = value.indexOf(aValue);
    return index;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "key" + ":" + getKey()+ "]"
     + outputString;
  }
}