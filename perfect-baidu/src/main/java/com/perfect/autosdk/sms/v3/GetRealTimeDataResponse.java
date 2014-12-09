/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 110 "../../../../../../../SDKDemo.ump"
public class GetRealTimeDataResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetRealTimeDataResponse Attributes
  private List<RealTimeResultType> realTimeResultTypes;

  //------------------------
  // INTERFACE
  //------------------------

  public void setRealTimeResultTypes(List<RealTimeResultType> arealTimeResultTypes){
    realTimeResultTypes = arealTimeResultTypes;
  }

  public boolean addRealTimeResultType(RealTimeResultType aRealTimeResultType)
  {
    boolean wasAdded = false;
    wasAdded = realTimeResultTypes.add(aRealTimeResultType);
    return wasAdded;
  }

  public boolean removeRealTimeResultType(RealTimeResultType aRealTimeResultType)
  {
    boolean wasRemoved = false;
    wasRemoved = realTimeResultTypes.remove(aRealTimeResultType);
    return wasRemoved;
  }

  public RealTimeResultType getRealTimeResultType(int index)
  {
    RealTimeResultType aRealTimeResultType = realTimeResultTypes.get(index);
    return aRealTimeResultType;
  }

  public List<RealTimeResultType> getRealTimeResultTypes()
  {
    return realTimeResultTypes;
  }

  public int numberOfRealTimeResultTypes()
  {
    int number = realTimeResultTypes.size();
    return number;
  }

  public boolean hasRealTimeResultTypes()
  {
    boolean has = realTimeResultTypes.size() > 0;
    return has;
  }

  public int indexOfRealTimeResultType(RealTimeResultType aRealTimeResultType)
  {
    int index = realTimeResultTypes.indexOf(aRealTimeResultType);
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