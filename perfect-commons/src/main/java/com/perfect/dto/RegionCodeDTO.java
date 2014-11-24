package com.perfect.dto;

/**
 * Created by baizz on 2014-9-19.
 */
public class RegionCodeDTO {

    private Long regionId;

    private String regionName;

    private Integer rank;

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "RegionCodeDTO{" +
                "regionId=" + regionId +
                ", regionName='" + regionName + '\'' +
                ", rank=" + rank +
                '}';
    }
}
