package com.perfect.service;

import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.utils.paging.PagerInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-11-26.
 */
public interface AdgroupService {

    List<AdgroupDTO> getAdgroupByCampaignId(Long campaignId, Map<String, Object> params, int skip, int limit);

    List<AdgroupDTO> getAdgroupByCampaignObjId(String campaignObjId);

    List<AdgroupDTO> getAdgroupByCampaignId(Long campaignObjId);

    List<Long> getAdgroupIdByCampaignId(Long campaignId);

    AdgroupDTO findOne(Long id);

    List<AdgroupDTO> find(Map<String, Object> params, int skip, int limit);

    void insertAll(List<AdgroupDTO> entities);

    void update(AdgroupDTO adgroupDTO);

    void delete(Long id);

    void deleteByIds(List<Long> ids);

    PagerInfo findByPagerInfo(Map<String, Object> params, Integer nowPage, Integer pageSize);

    Object insertOutId(AdgroupDTO adgroupEntity);

    void deleteByObjId(String oid);

    void deleteByObjId(Long adgroupId);

    AdgroupDTO findByObjId(String oid);

    void updateByObjId(AdgroupDTO adgroupEntity);

    void update(AdgroupDTO adgroupEntity, AdgroupDTO bakAdgroupEntity);

    void delBack(Long oid);

    AdgroupDTO fndEntity(Map<String, Object> params);

    void save(AdgroupDTO adgroupDTO);
}
