/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 151 "../../../../../../../SDKDemo.ump"
public class GetSeedWordResponse
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetSeedWordResponse Attributes
  private List<String> seedWords;

  //------------------------
  // INTERFACE
  //------------------------

  public void setSeedWords(List<String> aseedWords){
    seedWords = aseedWords;
  }

  public boolean addSeedWord(String aSeedWord)
  {
    boolean wasAdded = false;
    wasAdded = seedWords.add(aSeedWord);
    return wasAdded;
  }

  public boolean removeSeedWord(String aSeedWord)
  {
    boolean wasRemoved = false;
    wasRemoved = seedWords.remove(aSeedWord);
    return wasRemoved;
  }

  public String getSeedWord(int index)
  {
    String aSeedWord = seedWords.get(index);
    return aSeedWord;
  }

  public List<String> getSeedWords()
  {
    return seedWords;
  }

  public int numberOfSeedWords()
  {
    int number = seedWords.size();
    return number;
  }

  public boolean hasSeedWords()
  {
    boolean has = seedWords.size() > 0;
    return has;
  }

  public int indexOfSeedWord(String aSeedWord)
  {
    int index = seedWords.indexOf(aSeedWord);
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