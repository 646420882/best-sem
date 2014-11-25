package com.perfect.dto.regional;

/**
 * Created by SubDong on 2014/9/28.
 */
// TODO 跟RegionCodeDTO 的区别是啥子
public class RegionalCodeDTO {

    private String id;
    //国家id
    private String stateId;
    //国家名称
    private String stateName;
    //省级ID
    private String provinceId;
    //省级名称
    private String provinceName;
    //地区ID
    private String regionId;
    //地区名称
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
