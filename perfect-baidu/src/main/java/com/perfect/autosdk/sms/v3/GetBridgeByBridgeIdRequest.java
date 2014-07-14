/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 260 "../../../../../../../SDKDemo.ump"
public class GetBridgeByBridgeIdRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetBridgeByBridgeIdRequest Attributes
  private List<Long> bridgeIds;

  //------------------------
  // INTERFACE
  //------------------------

  public void setBridgeIds(List<Long> abridgeIds){
    bridgeIds = abridgeIds;
  }

  public boolean addBridgeId(Long aBridgeId)
  {
    boolean wasAdded = false;
    wasAdded = bridgeIds.add(aBridgeId);
    return wasAdded;
  }

  public boolean removeBridgeId(Long aBridgeId)
  {
    boolean wasRemoved = false;
    wasRemoved = bridgeIds.remove(aBridgeId);
    return wasRemoved;
  }

  public Long getBridgeId(int index)
  {
    Long aBridgeId = bridgeIds.get(index);
    return aBridgeId;
  }

  public List<Long> getBridgeIds()
  {
    return bridgeIds;
  }

  public int numberOfBridgeIds()
  {
    int number = bridgeIds.size();
    return number;
  }

  public boolean hasBridgeIds()
  {
    boolean has = bridgeIds.size() > 0;
    return has;
  }

  public int indexOfBridgeId(Long aBridgeId)
  {
    int index = bridgeIds.indexOf(aBridgeId);
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