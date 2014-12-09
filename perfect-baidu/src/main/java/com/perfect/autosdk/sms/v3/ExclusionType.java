/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;

// line 11 "../../../../../../../SDKDemo.ump"
public class ExclusionType
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ExclusionType Attributes
  private Long exclusionId;
  private String exclusionContent;
  private Integer exclusionType;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setExclusionId(Long aExclusionId)
  {
    boolean wasSet = false;
    exclusionId = aExclusionId;
    wasSet = true;
    return wasSet;
  }

  public boolean setExclusionContent(String aExclusionContent)
  {
    boolean wasSet = false;
    exclusionContent = aExclusionContent;
    wasSet = true;
    return wasSet;
  }

  public boolean setExclusionType(Integer aExclusionType)
  {
    boolean wasSet = false;
    exclusionType = aExclusionType;
    wasSet = true;
    return wasSet;
  }

  public Long getExclusionId()
  {
    return exclusionId;
  }

  public String getExclusionContent()
  {
    return exclusionContent;
  }

  public Integer getExclusionType()
  {
    return exclusionType;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "exclusionId" + ":" + getExclusionId()+ "," +
            "exclusionContent" + ":" + getExclusionContent()+ "," +
            "exclusionType" + ":" + getExclusionType()+ "]"
     + outputString;
  }
}