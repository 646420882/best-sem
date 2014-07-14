/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 206 "../../../../../../../SDKDemo.ump"
public class GetPhoneByPhoneIdRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetPhoneByPhoneIdRequest Attributes
  private List<Long> phoneIds;

  //------------------------
  // INTERFACE
  //------------------------

  public void setPhoneIds(List<Long> aphoneIds){
    phoneIds = aphoneIds;
  }

  public boolean addPhoneId(Long aPhoneId)
  {
    boolean wasAdded = false;
    wasAdded = phoneIds.add(aPhoneId);
    return wasAdded;
  }

  public boolean removePhoneId(Long aPhoneId)
  {
    boolean wasRemoved = false;
    wasRemoved = phoneIds.remove(aPhoneId);
    return wasRemoved;
  }

  public Long getPhoneId(int index)
  {
    Long aPhoneId = phoneIds.get(index);
    return aPhoneId;
  }

  public List<Long> getPhoneIds()
  {
    return phoneIds;
  }

  public int numberOfPhoneIds()
  {
    int number = phoneIds.size();
    return number;
  }

  public boolean hasPhoneIds()
  {
    boolean has = phoneIds.size() > 0;
    return has;
  }

  public int indexOfPhoneId(Long aPhoneId)
  {
    int index = phoneIds.indexOf(aPhoneId);
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