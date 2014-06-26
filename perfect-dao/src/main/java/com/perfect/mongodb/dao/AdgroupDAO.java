/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/
package com.perfect.mongodb.dao;

import com.perfect.mongodb.entity.AdgroupEntity;

import java.util.List;

public interface AdgroupDAO {

    // ABSTRACT METHODS

    public List<AdgroupEntity> getAdgroupByCampaignId(Object campaginId);

    public AdgroupEntity getAdgroupById(Object adgroupId);

    public List<AdgroupEntity> findAdgroup(AdgroupEntity adgroupEntity);

    public int addAdgroup(AdgroupEntity adgroupEntity);

    public int updateAdgroup(AdgroupEntity adgroupEntity);

    public int deleteAdgroup(AdgroupEntity adgroupEntity);


}