/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.List;

// line 150 "../../../../../../../SDKDemo.ump"
public class GetRealTimePairDataResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetRealTimePairDataResponse Attributes
  private List<RealTimePairResultType> realTimePairResultTypes;

  //------------------------
  // INTERFACE
  //------------------------

  public void setRealTimePairResultTypes(List<RealTimePairResultType> arealTimePairResultTypes){
    realTimePairResultTypes = arealTimePairResultTypes;
  }

  public boolean addRealTimePairResultType(RealTimePairResultType aRealTimePairResultType)
  {
    boolean wasAdded = false;
    wasAdded = realTimePairResultTypes.add(aRealTimePairResultType);
    return wasAdded;
  }

  public boolean removeRealTimePairResultType(RealTimePairResultType aRealTimePairResultType)
  {
    boolean wasRemoved = false;
    wasRemoved = realTimePairResultTypes.remove(aRealTimePairResultType);
    return wasRemoved;
  }

  public RealTimePairResultType getRealTimePairResultType(int index)
  {
    RealTimePairResultType aRealTimePairResultType = realTimePairResultTypes.get(index);
    return aRealTimePairResultType;
  }

  public List<RealTimePairResultType> getRealTimePairResultTypes()
  {
    return realTimePairResultTypes;
  }

  public int numberOfRealTimePairResultTypes()
  {
    int number = realTimePairResultTypes.size();
    return number;
  }

  public boolean hasRealTimePairResultTypes()
  {
    boolean has = realTimePairResultTypes.size() > 0;
    return has;
  }

  public int indexOfRealTimePairResultType(RealTimePairResultType aRealTimePairResultType)
  {
    int index = realTimePairResultTypes.indexOf(aRealTimePairResultType);
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