/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 48 "../../../../../../../SDKDemo.ump"
public class IPMaterialQueryRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //IPMaterialQueryRequest Attributes
  private Long materialType;
  private List<Long> materialIds;
  private List<Integer> isDel;
  private String nameLike;
  private Integer targetType;
  private List<Long> targetIds;
  private Integer offset;
  private Integer num;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setMaterialType(Long aMaterialType)
  {
    boolean wasSet = false;
    materialType = aMaterialType;
    wasSet = true;
    return wasSet;
  }

  public void setMaterialIds(List<Long> amaterialIds){
    materialIds = amaterialIds;
  }

  public boolean addMaterialId(Long aMaterialId)
  {
    boolean wasAdded = false;
    wasAdded = materialIds.add(aMaterialId);
    return wasAdded;
  }

  public boolean removeMaterialId(Long aMaterialId)
  {
    boolean wasRemoved = false;
    wasRemoved = materialIds.remove(aMaterialId);
    return wasRemoved;
  }

  public void setIsDel(List<Integer> aisDel){
    isDel = aisDel;
  }

  public boolean addIsDel(Integer aIsDel)
  {
    boolean wasAdded = false;
    wasAdded = isDel.add(aIsDel);
    return wasAdded;
  }

  public boolean removeIsDel(Integer aIsDel)
  {
    boolean wasRemoved = false;
    wasRemoved = isDel.remove(aIsDel);
    return wasRemoved;
  }

  public boolean setNameLike(String aNameLike)
  {
    boolean wasSet = false;
    nameLike = aNameLike;
    wasSet = true;
    return wasSet;
  }

  public boolean setTargetType(Integer aTargetType)
  {
    boolean wasSet = false;
    targetType = aTargetType;
    wasSet = true;
    return wasSet;
  }

  public void setTargetIds(List<Long> atargetIds){
    targetIds = atargetIds;
  }

  public boolean addTargetId(Long aTargetId)
  {
    boolean wasAdded = false;
    wasAdded = targetIds.add(aTargetId);
    return wasAdded;
  }

  public boolean removeTargetId(Long aTargetId)
  {
    boolean wasRemoved = false;
    wasRemoved = targetIds.remove(aTargetId);
    return wasRemoved;
  }

  public boolean setOffset(Integer aOffset)
  {
    boolean wasSet = false;
    offset = aOffset;
    wasSet = true;
    return wasSet;
  }

  public boolean setNum(Integer aNum)
  {
    boolean wasSet = false;
    num = aNum;
    wasSet = true;
    return wasSet;
  }

  public Long getMaterialType()
  {
    return materialType;
  }

  public Long getMaterialId(int index)
  {
    Long aMaterialId = materialIds.get(index);
    return aMaterialId;
  }

  public List<Long> getMaterialIds()
  {
    return materialIds;
  }

  public int numberOfMaterialIds()
  {
    int number = materialIds.size();
    return number;
  }

  public boolean hasMaterialIds()
  {
    boolean has = materialIds.size() > 0;
    return has;
  }

  public int indexOfMaterialId(Long aMaterialId)
  {
    int index = materialIds.indexOf(aMaterialId);
    return index;
  }

  public Integer getIsDel(int index)
  {
    Integer aIsDel = isDel.get(index);
    return aIsDel;
  }

  public List<Integer> getIsDel()
  {
    return isDel;
  }

  public int numberOfIsDel()
  {
    int number = isDel.size();
    return number;
  }

  public boolean hasIsDel()
  {
    boolean has = isDel.size() > 0;
    return has;
  }

  public int indexOfIsDel(Integer aIsDel)
  {
    int index = isDel.indexOf(aIsDel);
    return index;
  }

  public String getNameLike()
  {
    return nameLike;
  }

  public Integer getTargetType()
  {
    return targetType;
  }

  public Long getTargetId(int index)
  {
    Long aTargetId = targetIds.get(index);
    return aTargetId;
  }

  public List<Long> getTargetIds()
  {
    return targetIds;
  }

  public int numberOfTargetIds()
  {
    int number = targetIds.size();
    return number;
  }

  public boolean hasTargetIds()
  {
    boolean has = targetIds.size() > 0;
    return has;
  }

  public int indexOfTargetId(Long aTargetId)
  {
    int index = targetIds.indexOf(aTargetId);
    return index;
  }

  public Integer getOffset()
  {
    return offset;
  }

  public Integer getNum()
  {
    return num;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "materialType" + ":" + getMaterialType()+ "," +
            "nameLike" + ":" + getNameLike()+ "," +
            "targetType" + ":" + getTargetType()+ "," +
            "offset" + ":" + getOffset()+ "," +
            "num" + ":" + getNum()+ "]"
     + outputString;
  }
}