package com.perfect.service;

import com.perfect.dto.adgroup.AdgroupDTO;

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


}
