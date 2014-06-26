/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.List;

// line 48 "../../../../../../../SDKDemo.ump"
public class GetAttributeByWordResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetAttributeByWordResponse Attributes
  private List<WordAttribute> wordAttributes;

  //------------------------
  // INTERFACE
  //------------------------

  public void setWordAttributes(List<WordAttribute> awordAttributes){
    wordAttributes = awordAttributes;
  }

  public boolean addWordAttribute(WordAttribute aWordAttribute)
  {
    boolean wasAdded = false;
    wasAdded = wordAttributes.add(aWordAttribute);
    return wasAdded;
  }

  public boolean removeWordAttribute(WordAttribute aWordAttribute)
  {
    boolean wasRemoved = false;
    wasRemoved = wordAttributes.remove(aWordAttribute);
    return wasRemoved;
  }

  public WordAttribute getWordAttribute(int index)
  {
    WordAttribute aWordAttribute = wordAttributes.get(index);
    return aWordAttribute;
  }

  public List<WordAttribute> getWordAttributes()
  {
    return wordAttributes;
  }

  public int numberOfWordAttributes()
  {
    int number = wordAttributes.size();
    return number;
  }

  public boolean hasWordAttributes()
  {
    boolean has = wordAttributes.size() > 0;
    return has;
  }

  public int indexOfWordAttribute(WordAttribute aWordAttribute)
  {
    int index = wordAttributes.indexOf(aWordAttribute);
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