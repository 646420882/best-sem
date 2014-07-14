/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 22 "../../../../../../../SDKDemo.ump"
public class IPMaterialDTO
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //IPMaterialDTO Attributes
  private Long materialId;
  private Long bindId;
  private Long materialType;
  private String materialName;
  private Integer status;
  private Long reasonId;
  private String reasonText;
  private Long planid;
  private String planname;
  private Long unitid;
  private String unitname;
  private String materialContent;
  private String auditContent;
  private Integer isPause;
  private Integer isDel;
  private Integer auditStatus;
  private List<IPBindDTO> bind;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setMaterialId(Long aMaterialId)
  {
    boolean wasSet = false;
    materialId = aMaterialId;
    wasSet = true;
    return wasSet;
  }

  public boolean setBindId(Long aBindId)
  {
    boolean wasSet = false;
    bindId = aBindId;
    wasSet = true;
    return wasSet;
  }

  public boolean setMaterialType(Long aMaterialType)
  {
    boolean wasSet = false;
    materialType = aMaterialType;
    wasSet = true;
    return wasSet;
  }

  public boolean setMaterialName(String aMaterialName)
  {
    boolean wasSet = false;
    materialName = aMaterialName;
    wasSet = true;
    return wasSet;
  }

  public boolean setStatus(Integer aStatus)
  {
    boolean wasSet = false;
    status = aStatus;
    wasSet = true;
    return wasSet;
  }

  public boolean setReasonId(Long aReasonId)
  {
    boolean wasSet = false;
    reasonId = aReasonId;
    wasSet = true;
    return wasSet;
  }

  public boolean setReasonText(String aReasonText)
  {
    boolean wasSet = false;
    reasonText = aReasonText;
    wasSet = true;
    return wasSet;
  }

  public boolean setPlanid(Long aPlanid)
  {
    boolean wasSet = false;
    planid = aPlanid;
    wasSet = true;
    return wasSet;
  }

  public boolean setPlanname(String aPlanname)
  {
    boolean wasSet = false;
    planname = aPlanname;
    wasSet = true;
    return wasSet;
  }

  public boolean setUnitid(Long aUnitid)
  {
    boolean wasSet = false;
    unitid = aUnitid;
    wasSet = true;
    return wasSet;
  }

  public boolean setUnitname(String aUnitname)
  {
    boolean wasSet = false;
    unitname = aUnitname;
    wasSet = true;
    return wasSet;
  }

  public boolean setMaterialContent(String aMaterialContent)
  {
    boolean wasSet = false;
    materialContent = aMaterialContent;
    wasSet = true;
    return wasSet;
  }

  public boolean setAuditContent(String aAuditContent)
  {
    boolean wasSet = false;
    auditContent = aAuditContent;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsPause(Integer aIsPause)
  {
    boolean wasSet = false;
    isPause = aIsPause;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsDel(Integer aIsDel)
  {
    boolean wasSet = false;
    isDel = aIsDel;
    wasSet = true;
    return wasSet;
  }

  public boolean setAuditStatus(Integer aAuditStatus)
  {
    boolean wasSet = false;
    auditStatus = aAuditStatus;
    wasSet = true;
    return wasSet;
  }

  public void setBind(List<IPBindDTO> abind){
    bind = abind;
  }

  public boolean addBind(IPBindDTO aBind)
  {
    boolean wasAdded = false;
    wasAdded = bind.add(aBind);
    return wasAdded;
  }

  public boolean removeBind(IPBindDTO aBind)
  {
    boolean wasRemoved = false;
    wasRemoved = bind.remove(aBind);
    return wasRemoved;
  }

  public Long getMaterialId()
  {
    return materialId;
  }

  public Long getBindId()
  {
    return bindId;
  }

  public Long getMaterialType()
  {
    return materialType;
  }

  public String getMaterialName()
  {
    return materialName;
  }

  public Integer getStatus()
  {
    return status;
  }

  public Long getReasonId()
  {
    return reasonId;
  }

  public String getReasonText()
  {
    return reasonText;
  }

  public Long getPlanid()
  {
    return planid;
  }

  public String getPlanname()
  {
    return planname;
  }

  public Long getUnitid()
  {
    return unitid;
  }

  public String getUnitname()
  {
    return unitname;
  }

  public String getMaterialContent()
  {
    return materialContent;
  }

  public String getAuditContent()
  {
    return auditContent;
  }

  public Integer getIsPause()
  {
    return isPause;
  }

  public Integer getIsDel()
  {
    return isDel;
  }

  public Integer getAuditStatus()
  {
    return auditStatus;
  }

  public IPBindDTO getBind(int index)
  {
    IPBindDTO aBind = bind.get(index);
    return aBind;
  }

  public List<IPBindDTO> getBind()
  {
    return bind;
  }

  public int numberOfBind()
  {
    int number = bind.size();
    return number;
  }

  public boolean hasBind()
  {
    boolean has = bind.size() > 0;
    return has;
  }

  public int indexOfBind(IPBindDTO aBind)
  {
    int index = bind.indexOf(aBind);
    return index;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "materialId" + ":" + getMaterialId()+ "," +
            "bindId" + ":" + getBindId()+ "," +
            "materialType" + ":" + getMaterialType()+ "," +
            "materialName" + ":" + getMaterialName()+ "," +
            "status" + ":" + getStatus()+ "," +
            "reasonId" + ":" + getReasonId()+ "," +
            "reasonText" + ":" + getReasonText()+ "," +
            "planid" + ":" + getPlanid()+ "," +
            "planname" + ":" + getPlanname()+ "," +
            "unitid" + ":" + getUnitid()+ "," +
            "unitname" + ":" + getUnitname()+ "," +
            "materialContent" + ":" + getMaterialContent()+ "," +
            "auditContent" + ":" + getAuditContent()+ "," +
            "isPause" + ":" + getIsPause()+ "," +
            "isDel" + ":" + getIsDel()+ "," +
            "auditStatus" + ":" + getAuditStatus()+ "]"
     + outputString;
  }
}