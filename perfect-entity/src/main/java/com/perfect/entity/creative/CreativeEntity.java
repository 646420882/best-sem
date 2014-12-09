/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.entity.creative;

import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.entity.account.AccountIdEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = MongoEntityConstants.TBL_CREATIVE)
public class CreativeEntity extends AccountIdEntity {
    //CreativeType Attributes
    @Id
    private String id;

    @Indexed(sparse = true)
    @Field(MongoEntityConstants.CREATIVE_ID)
    private Long creativeId;

    //------------------------
    // MEMBER VARIABLES
    //------------------------
    @Field(MongoEntityConstants.ADGROUP_ID)
    private Long adgroupId;


    @Field(MongoEntityConstants.OBJ_ADGROUP_ID)
    private String adgroupObjId;

    @Field("t")
    private String title;

    @Field("desc1")
    private String description1;

    @Field("desc2")
    private String description2;

    @Field("pc")
    private String pcDestinationUrl;

    @Field("pcd")
    private String pcDisplayUrl;

    @Field("m")
    private String mobileDestinationUrl;

    @Field("md")
    private String mobileDisplayUrl;

    @Field("p")
    private Boolean pause;

    @Field("s")
    private Integer status;

    @Field("d")
    private Integer devicePreference;

    @Field("ls")
    private Integer localStatus;//本地状态1为新增,2为修改,3为删除,4为级联删除标识

    public String getAdgroupObjId() {
        return adgroupObjId;
    }

    public void setAdgroupObjId(String adgroupObjId) {
        this.adgroupObjId = adgroupObjId;
    }

    public Integer getLocalStatus() {
        return localStatus;
    }

    public void setLocalStatus(Integer localStatus) {
        this.localStatus = localStatus;
    }

    public boolean setCreativeId(Long aCreativeId) {
        boolean wasSet = false;
        creativeId = aCreativeId;
        wasSet = true;
        return wasSet;
    }


    //------------------------
    // INTERFACE
    //------------------------

    public boolean setAdgroupId(Long aAdgroupId) {
        boolean wasSet = false;
        adgroupId = aAdgroupId;
        wasSet = true;
        return wasSet;
    }

    public boolean setTitle(String aTitle) {
        boolean wasSet = false;
        title = aTitle;
        wasSet = true;
        return wasSet;
    }

    public boolean setDescription1(String aDescription1) {
        boolean wasSet = false;
        description1 = aDescription1;
        wasSet = true;
        return wasSet;
    }

    public boolean setDescription2(String aDescription2) {
        boolean wasSet = false;
        description2 = aDescription2;
        wasSet = true;
        return wasSet;
    }

    public boolean setPcDestinationUrl(String aPcDestinationUrl) {
        boolean wasSet = false;
        pcDestinationUrl = aPcDestinationUrl;
        wasSet = true;
        return wasSet;
    }

    public boolean setPcDisplayUrl(String aPcDisplayUrl) {
        boolean wasSet = false;
        pcDisplayUrl = aPcDisplayUrl;
        wasSet = true;
        return wasSet;
    }

    public boolean setMobileDestinationUrl(String aMobileDestinationUrl) {
        boolean wasSet = false;
        mobileDestinationUrl = aMobileDestinationUrl;
        wasSet = true;
        return wasSet;
    }

    public boolean setMobileDisplayUrl(String aMobileDisplayUrl) {
        boolean wasSet = false;
        mobileDisplayUrl = aMobileDisplayUrl;
        wasSet = true;
        return wasSet;
    }

    public boolean setPause(Boolean aPause) {
        boolean wasSet = false;
        pause = aPause;
        wasSet = true;
        return wasSet;
    }

    public boolean setStatus(Integer aStatus) {
        boolean wasSet = false;
        status = aStatus;
        wasSet = true;
        return wasSet;
    }

    public boolean setDevicePreference(Integer aDevicePreference) {
        boolean wasSet = false;
        devicePreference = aDevicePreference;
        wasSet = true;
        return wasSet;
    }

    public Long getCreativeId() {
        return creativeId;
    }

    public Long getAdgroupId() {
        return adgroupId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription1() {
        return description1;
    }

    public String getDescription2() {
        return description2;
    }

    public String getPcDestinationUrl() {
        return pcDestinationUrl;
    }

    public String getPcDisplayUrl() {
        return pcDisplayUrl;
    }

    public String getMobileDestinationUrl() {
        return mobileDestinationUrl;
    }

    public String getMobileDisplayUrl() {
        return mobileDisplayUrl;
    }

    public Boolean getPause() {
        return pause;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getDevicePreference() {
        return devicePreference;
    }

    public void delete() {
    }

    public String toString() {
        String outputString = "";
        return super.toString() + "[" +
                "creativeId" + ":" + getCreativeId() + "," +
                "adgroupId" + ":" + getAdgroupId() + "," +
                "title" + ":" + getTitle() + "," +
                "description1" + ":" + getDescription1() + "," +
                "description2" + ":" + getDescription2() + "," +
                "pcDestinationUrl" + ":" + getPcDestinationUrl() + "," +
                "pcDisplayUrl" + ":" + getPcDisplayUrl() + "," +
                "mobileDestinationUrl" + ":" + getMobileDestinationUrl() + "," +
                "mobileDisplayUrl" + ":" + getMobileDisplayUrl() + "," +
                "pause" + ":" + getPause() + "," +
                "status" + ":" + getStatus() + "," +
                "devicePreference" + ":" + getDevicePreference() + "]"
                + outputString;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}