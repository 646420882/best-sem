/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 145 "../../../../../../../SDKDemo.ump"
public class GetRealTimeQueryDataResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetRealTimeQueryDataResponse Attributes
  private List<RealTimeQueryResultType> realTimeQueryResultTypes;

  //------------------------
  // INTERFACE
  //------------------------

  public void setRealTimeQueryResultTypes(List<RealTimeQueryResultType> arealTimeQueryResultTypes){
    realTimeQueryResultTypes = arealTimeQueryResultTypes;
  }

  public boolean addRealTimeQueryResultType(RealTimeQueryResultType aRealTimeQueryResultType)
  {
    boolean wasAdded = false;
    wasAdded = realTimeQueryResultTypes.add(aRealTimeQueryResultType);
    return wasAdded;
  }

  public boolean removeRealTimeQueryResultType(RealTimeQueryResultType aRealTimeQueryResultType)
  {
    boolean wasRemoved = false;
    wasRemoved = realTimeQueryResultTypes.remove(aRealTimeQueryResultType);
    return wasRemoved;
  }

  public RealTimeQueryResultType getRealTimeQueryResultType(int index)
  {
    RealTimeQueryResultType aRealTimeQueryResultType = realTimeQueryResultTypes.get(index);
    return aRealTimeQueryResultType;
  }

  public List<RealTimeQueryResultType> getRealTimeQueryResultTypes()
  {
    return realTimeQueryResultTypes;
  }

  public int numberOfRealTimeQueryResultTypes()
  {
    int number = realTimeQueryResultTypes.size();
    return number;
  }

  public boolean hasRealTimeQueryResultTypes()
  {
    boolean has = realTimeQueryResultTypes.size() > 0;
    return has;
  }

  public int indexOfRealTimeQueryResultType(RealTimeQueryResultType aRealTimeQueryResultType)
  {
    int index = realTimeQueryResultTypes.indexOf(aRealTimeQueryResultType);
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