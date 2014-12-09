/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 71 "../../../../../../../SDKDemo.ump"
public class SeedUrl
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //SeedUrl Attributes
  private String url;
  private List<String> urlTag;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setUrl(String aUrl)
  {
    boolean wasSet = false;
    url = aUrl;
    wasSet = true;
    return wasSet;
  }

  public void setUrlTag(List<String> aurlTag){
    urlTag = aurlTag;
  }

  public boolean addUrlTag(String aUrlTag)
  {
    boolean wasAdded = false;
    wasAdded = urlTag.add(aUrlTag);
    return wasAdded;
  }

  public boolean removeUrlTag(String aUrlTag)
  {
    boolean wasRemoved = false;
    wasRemoved = urlTag.remove(aUrlTag);
    return wasRemoved;
  }

  public String getUrl()
  {
    return url;
  }

  public String getUrlTag(int index)
  {
    String aUrlTag = urlTag.get(index);
    return aUrlTag;
  }

  public List<String> getUrlTag()
  {
    return urlTag;
  }

  public int numberOfUrlTag()
  {
    int number = urlTag.size();
    return number;
  }

  public boolean hasUrlTag()
  {
    boolean has = urlTag.size() > 0;
    return has;
  }

  public int indexOfUrlTag(String aUrlTag)
  {
    int index = urlTag.indexOf(aUrlTag);
    return index;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "url" + ":" + getUrl()+ "]"
     + outputString;
  }
}