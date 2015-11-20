/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk_v4.sms.service;
import com.perfect.autosdk_v4.common.*;
import java.util.*;

// line 70 "../../../../../../../SDK.ump"
public class GetKRByQueryRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetKRByQueryRequest Attributes
  private Integer queryType;
  private SeedFilter seedFilter;
  private String query;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setQueryType(Integer aQueryType)
  {
    boolean wasSet = false;
    queryType = aQueryType;
    wasSet = true;
    return wasSet;
  }

  public boolean setSeedFilter(SeedFilter aSeedFilter)
  {
    boolean wasSet = false;
    seedFilter = aSeedFilter;
    wasSet = true;
    return wasSet;
  }

  public boolean setQuery(String aQuery)
  {
    boolean wasSet = false;
    query = aQuery;
    wasSet = true;
    return wasSet;
  }

  public Integer getQueryType()
  {
    return queryType;
  }

  public SeedFilter getSeedFilter()
  {
    return seedFilter;
  }

  public String getQuery()
  {
    return query;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "queryType" + ":" + getQueryType()+ "," +
            "query" + ":" + getQuery()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "seedFilter" + "=" + (getSeedFilter() != null ? !getSeedFilter().equals(this)  ? getSeedFilter().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}