package com.perfect.dao.creative;

import com.perfect.dao.MongoCrudRepository;
import com.perfect.dto.creative.CreativeDTO;
import com.perfect.paging.PagerInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-7-10.
 */
public interface CreativeDAO extends MongoCrudRepository<CreativeDTO, Long> {

    List<Long> getCreativeIdByAdgroupId(Long adgroupId);

    List<CreativeDTO> findByAgroupId(Long adgroupId);

    List<CreativeDTO> getCreativeByAdgroupId(Long adgroupId, Map<String, Object> params, int skip, int limit);

    List<CreativeDTO> getCreativeByAdgroupId(String adgroupId, Map<String, Object> params, int skip, int limit);

    List<CreativeDTO> getAllsByAdgroupIds(List<Long> l);

    List<CreativeDTO> getAllsByAdgroupIdsForString(List<String> l);

    void deleteByCacheId(Long cacheCreativeId);

    void deleteByCacheId(String cacheCreativeId);

    String insertOutId(CreativeDTO creativeEntity);

    void insertByReBack(CreativeDTO oldcreativeEntity);

    CreativeDTO findByObjId(String obj);

    CreativeDTO getAllsBySomeParams(Map<String, Object> params);

    void updateByObjId(CreativeDTO creativeEntity);

    void update(CreativeDTO newCreativeEntity, CreativeDTO creativeBackUpEntity);

    void updateAdgroupIdByOid(String id, Long adgroupId);

    void delBack(Long oid);

    PagerInfo findByPagerInfo(Map<String, Object> params, Integer nowPage, Integer pageSize);

    PagerInfo findByPagerInfoForString(List<String> l, Integer nowPage, Integer pageSize);

    PagerInfo findByPagerInfoForLong(List<Long> l, Integer nowPage, Integer pageSize);

    PagerInfo findByPagerInfo(Long l, Integer nowPage, Integer pageSize);

}
