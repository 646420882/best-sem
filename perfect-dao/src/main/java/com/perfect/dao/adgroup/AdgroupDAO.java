/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/
package com.perfect.dao.adgroup;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.utils.paging.PagerInfo;

import java.util.List;
import java.util.Map;

public interface AdgroupDAO extends HeyCrudRepository<AdgroupDTO, Long> {

    List<Long> getAllAdgroupId();

    List<Long> getAdgroupIdByCampaignId(Long campaignId);

    List<String> getAdgroupIdByCampaignId(String campaignId);

    List<AdgroupDTO> getAdgroupByCampaignId(Long campaignId, Map<String, Object> params, int skip, int limit);

    List<AdgroupDTO> getAdgroupByCampaignObjId(String campaignObjId);

    List<AdgroupDTO> getAdgroupByCampaignId(Long campaignId);

//    List<AdgroupDTO> findByQuery(Query query);

    List<AdgroupDTO> findByCampaignId(Long cid);

    List<AdgroupDTO> findIdByCampaignId(Long cid);

    AdgroupDTO findByObjId(String oid);

    AdgroupDTO fndEntity(Map<String, Object> params);

    //2014-11-24 refactor
    Object insertOutId(AdgroupDTO adgroupEntity);

    void deleteByObjId(String oid);

    void deleteByObjId(Long adgroupId);

    void updateCampaignIdByOid(String oid, Long campaignId);

    void updateByObjId(AdgroupDTO adgroupEntity);

    void update(AdgroupDTO adgroupEntity, AdgroupDTO bakAdgroupEntity);

    void update(AdgroupDTO adgroupEntity);

    void insertReBack(AdgroupDTO adgroupEntity);

    void delBack(Long oid);

    PagerInfo findByPagerInfo(Map<String, Object> params, Integer nowPage, Integer pageSize);

    AdgroupDTO getByCampaignIdAndName(Long campaignId, String name);

    List<AdgroupDTO> findByCampaignOId(String id);

    List<String> getObjAdgroupIdByCampaignId(List<String> cids);

    void deleteLinkedByAgid(List<Long> agid);

    void insert(AdgroupDTO adgroupDTO);

}