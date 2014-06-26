/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.List;

// line 164 "../../../../../../../SDKDemo.ump"
public class GetSeedUrlResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetSeedUrlResponse Attributes
  private List<SeedUrl> seedUrls;

  //------------------------
  // INTERFACE
  //------------------------

  public void setSeedUrls(List<SeedUrl> aseedUrls){
    seedUrls = aseedUrls;
  }

  public boolean addSeedUrl(SeedUrl aSeedUrl)
  {
    boolean wasAdded = false;
    wasAdded = seedUrls.add(aSeedUrl);
    return wasAdded;
  }

  public boolean removeSeedUrl(SeedUrl aSeedUrl)
  {
    boolean wasRemoved = false;
    wasRemoved = seedUrls.remove(aSeedUrl);
    return wasRemoved;
  }

  public SeedUrl getSeedUrl(int index)
  {
    SeedUrl aSeedUrl = seedUrls.get(index);
    return aSeedUrl;
  }

  public List<SeedUrl> getSeedUrls()
  {
    return seedUrls;
  }

  public int numberOfSeedUrls()
  {
    int number = seedUrls.size();
    return number;
  }

  public boolean hasSeedUrls()
  {
    boolean has = seedUrls.size() > 0;
    return has;
  }

  public int indexOfSeedUrl(SeedUrl aSeedUrl)
  {
    int index = seedUrls.indexOf(aSeedUrl);
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