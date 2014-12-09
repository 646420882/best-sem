package com.perfect.entity;

import com.perfect.commons.constants.RegionalConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by SubDong on 2014/11/26.
 */
@Document(collection = RegionalConstants.TBL_SYS_REGIONAL)
public class RegionalCodeEntity {

    @Id
    private String id;
    //国家id
    @Field(value = "stateId")
    private String stateId;
    //国家名称
    @Field(value = "stateName")
    private String stateName;
    //省级ID
    @Field(value = "provinceId")
    private String provinceId;
    //省级名称
    @Field(value = "provinceName")
    private String provinceName;
    //地区ID
    @Field(value = "regionId")
    private String regionId;
    //地区名称
    @Field(value = "regionName")
    private String regionName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @Override
    public String toString() {
        return "RedisRegional{" +
                "id='" + id + '\'' +
                ", stateId='" + stateId + '\'' +
                ", stateName='" + stateName + '\'' +
                ", provinceId='" + provinceId + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", regionId='" + regionId + '\'' +
                ", regionName='" + regionName + '\'' +
                '}';
    }
}
