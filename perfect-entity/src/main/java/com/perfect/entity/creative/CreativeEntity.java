/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.entity.creative;

import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.entity.account.AccountIdEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @description 创意实体类
 */
@Document(collection = MongoEntityConstants.TBL_CREATIVE)
public class CreativeEntity extends AccountIdEntity {
    //CreativeType Attributes
    @Id
    private String id;

    @Indexed(sparse = true)
    @Field(MongoEntityConstants.CREATIVE_ID)
    private Long creativeId;                                // 创意ID

    //------------------------
    // MEMBER VARIABLES
    //------------------------
    @Field(MongoEntityConstants.ADGROUP_ID)
    private Long adgroupId;                                 // 百度推广单元ID


    @Field(MongoEntityConstants.OBJ_ADGROUP_ID)
    private String adgroupObjId;                            // 本地操作推广单元ID

    @Field("t")
    private String title;                                   // 创意标题

    @Field("desc1")
    private String description1;                            // 创意描述一

    @Field("desc2")
    private String description2;                            // 创意描述二

    @Field("pc")
    private String pcDestinationUrl;                        // PC目标URL

    @Field("pcd")
    private String pcDisplayUrl;                            // PC显示URL

    @Field("m")
    private String mobileDestinationUrl;                    // 移动目标URL

    @Field("md")
    private String mobileDisplayUrl;                        // 移动显示URL

    @Field("p")
    private Boolean pause;                                  // 暂停/启动 创意

    @Field("s")
    private Integer status;                                 // 创意状态

    @Field("d")
    private Integer devicePreference;                       // 设备偏好: 0->整合型, 1->移动设备优先

    @Field("ls")
    private Integer localStatus;                            //本地状态1为新增,2为修改,3为删除,4为级联删除标识

    @Field("k")
    private String key;

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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void delete() {
    }

    @Override
    public String toString() {
        return "CreativeEntity{" +
                "id='" + id + '\'' +
                ", creativeId=" + creativeId +
                ", adgroupId=" + adgroupId +
                ", adgroupObjId='" + adgroupObjId + '\'' +
                ", title='" + title + '\'' +
                ", description1='" + description1 + '\'' +
                ", description2='" + description2 + '\'' +
                ", pcDestinationUrl='" + pcDestinationUrl + '\'' +
                ", pcDisplayUrl='" + pcDisplayUrl + '\'' +
                ", mobileDestinationUrl='" + mobileDestinationUrl + '\'' +
                ", mobileDisplayUrl='" + mobileDisplayUrl + '\'' +
                ", pause=" + pause +
                ", status=" + status +
                ", devicePreference=" + devicePreference +
                ", localStatus=" + localStatus +
                ", key='" + key + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}