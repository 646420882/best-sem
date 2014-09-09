/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/
package com.perfect.dao;

import com.perfect.entity.AdgroupEntity;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Map;

public interface AdgroupDAO extends MongoCrudRepository<AdgroupEntity, Long> {

    List<Long> getAllAdgroupId();

    List<Long> getAdgroupIdByCampaignId(Long campaignId);

    List<String> getAdgroupIdByCampaignId(String campaignId);

    List<AdgroupEntity> getAdgroupByCampaignId(Long campaignId, Map<String, Object> params, int skip, int limit);

    List<AdgroupEntity> getAdgroupByCampaignObjId(String campaignObjId);

    List<AdgroupEntity> findByQuery(Query query);

    List<AdgroupEntity> findByCampaignId(Long cid);

    List<AdgroupEntity> findIdByCampaignId(Long cid);

    AdgroupEntity findByObjId(String oid);

    Object insertOutId(AdgroupEntity adgroupEntity);

    void deleteByObjId(String oid);

    void deleteByObjId(Long adgroupId);

    void updateCampaignIdByOid(String oid, Long campaignId);

    void updateByObjId(AdgroupEntity adgroupEntity);
    void update(AdgroupEntity adgroupEntity,AdgroupEntity bakAdgroupEntity);
    void insertReBack(AdgroupEntity adgroupEntity);
    void delBack(Long oid);
}