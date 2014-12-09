/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;

// line 74 "../../../../../../../SDKDemo.ump"
public class GetkeywordRankResponseMetaData
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetkeywordRankResponseMetaData Attributes
  private Long winfoid;
  private Long adgroupId;
  private Integer leftpercent;
  private Double leftRank;
  private Double rightRank;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setWinfoid(Long aWinfoid)
  {
    boolean wasSet = false;
    winfoid = aWinfoid;
    wasSet = true;
    return wasSet;
  }

  public boolean setAdgroupId(Long aAdgroupId)
  {
    boolean wasSet = false;
    adgroupId = aAdgroupId;
    wasSet = true;
    return wasSet;
  }

  public boolean setLeftpercent(Integer aLeftpercent)
  {
    boolean wasSet = false;
    leftpercent = aLeftpercent;
    wasSet = true;
    return wasSet;
  }

  public boolean setLeftRank(Double aLeftRank)
  {
    boolean wasSet = false;
    leftRank = aLeftRank;
    wasSet = true;
    return wasSet;
  }

  public boolean setRightRank(Double aRightRank)
  {
    boolean wasSet = false;
    rightRank = aRightRank;
    wasSet = true;
    return wasSet;
  }

  public Long getWinfoid()
  {
    return winfoid;
  }

  public Long getAdgroupId()
  {
    return adgroupId;
  }

  public Integer getLeftpercent()
  {
    return leftpercent;
  }

  public Double getLeftRank()
  {
    return leftRank;
  }

  public Double getRightRank()
  {
    return rightRank;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "winfoid" + ":" + getWinfoid()+ "," +
            "adgroupId" + ":" + getAdgroupId()+ "," +
            "leftpercent" + ":" + getLeftpercent()+ "," +
            "leftRank" + ":" + getLeftRank()+ "," +
            "rightRank" + ":" + getRightRank()+ "]"
     + outputString;
  }
}