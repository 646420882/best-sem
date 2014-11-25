package com.perfect.dao;

import com.perfect.dao.utils.PagerInfo;
import com.perfect.dto.creative.CreativeInfoDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-7-10.
 */
public interface CreativeDAO extends MongoCrudRepository<CreativeInfoDTO, Long> {

    List<Long> getCreativeIdByAdgroupId(Long adgroupId);

    List<CreativeInfoDTO> findByAgroupId(Long adgroupId);

    List<CreativeInfoDTO> getCreativeByAdgroupId(Long adgroupId, Map<String, Object> params, int skip, int limit);

    List<CreativeInfoDTO> getCreativeByAdgroupId(String adgroupId, Map<String, Object> params, int skip, int limit);

    List<CreativeInfoDTO> getAllsByAdgroupIds(List<Long> l);

    List<CreativeInfoDTO> getAllsByAdgroupIdsForString(List<String> l);

    void deleteByCacheId(Long cacheCreativeId);

    void deleteByCacheId(String cacheCreativeId);

    String insertOutId(CreativeInfoDTO creativeEntity);

    void insertByReBack(CreativeInfoDTO oldcreativeEntity);

    CreativeInfoDTO findByObjId(String obj);

    CreativeInfoDTO getAllsBySomeParams(Map<String, Object> params);

    void updateByObjId(CreativeInfoDTO creativeEntity);

    void update(CreativeInfoDTO newCreativeEntity, CreativeInfoDTO creativeBackUpEntity);

    void updateAdgroupIdByOid(String id, Long adgroupId);

    void delBack(Long oid);

    PagerInfo findByPagerInfo(Map<String, Object> params, Integer nowPage, Integer pageSize);

    PagerInfo findByPagerInfoForString(List<String> l, Integer nowPage, Integer pageSize);

    PagerInfo findByPagerInfoForLong(List<Long> l, Integer nowPage, Integer pageSize);

    PagerInfo findByPagerInfo(Long l, Integer nowPage, Integer pageSize);

}
