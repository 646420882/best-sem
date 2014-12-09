/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 13 "../../../../../../../SDKDemo.ump"
public class WordAttribute
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //WordAttribute Attributes
  private String searchWord;
  private List<String> attributes;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setSearchWord(String aSearchWord)
  {
    boolean wasSet = false;
    searchWord = aSearchWord;
    wasSet = true;
    return wasSet;
  }

  public void setAttributes(List<String> aattributes){
    attributes = aattributes;
  }

  public boolean addAttribute(String aAttribute)
  {
    boolean wasAdded = false;
    wasAdded = attributes.add(aAttribute);
    return wasAdded;
  }

  public boolean removeAttribute(String aAttribute)
  {
    boolean wasRemoved = false;
    wasRemoved = attributes.remove(aAttribute);
    return wasRemoved;
  }

  public String getSearchWord()
  {
    return searchWord;
  }

  public String getAttribute(int index)
  {
    String aAttribute = attributes.get(index);
    return aAttribute;
  }

  public List<String> getAttributes()
  {
    return attributes;
  }

  public int numberOfAttributes()
  {
    int number = attributes.size();
    return number;
  }

  public boolean hasAttributes()
  {
    boolean has = attributes.size() > 0;
    return has;
  }

  public int indexOfAttribute(String aAttribute)
  {
    int index = attributes.indexOf(aAttribute);
    return index;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "searchWord" + ":" + getSearchWord()+ "]"
     + outputString;
  }
}