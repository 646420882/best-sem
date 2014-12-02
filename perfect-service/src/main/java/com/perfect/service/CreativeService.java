package com.perfect.service;

import com.perfect.dto.creative.CreativeDTO;
import com.perfect.utils.paging.PagerInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by SubDong on 2014/11/26.
 */
public interface CreativeService {

    public List<Long> getCreativeIdByAdgroupId(Long adgroupId);

    public List<CreativeDTO> getCreativeByAdgroupId(Long adgroupId, Map<String, Object> params, int skip, int limit);

    public CreativeDTO findOne(Long creativeId);

    public List<CreativeDTO> find(Map<String, Object> params, int skip, int limit);

    void insertAll(List<CreativeDTO> list);

    void update(CreativeDTO creativeDTO);

    void delete(Long creativeId);

    void deleteByIds(List<Long> creativeIds);

    PagerInfo findByPagerInfo(Map<String,Object> map,int nowPage,int pageSize);

    PagerInfo findByPagerInfo(Long l, Integer nowPage, Integer pageSize);

    PagerInfo findByPagerInfoForLong(List<Long> longs,int nowpage,int pageSize);

    String insertOutId(CreativeDTO creativeEntity);

    void deleteByCacheId(Long cacheCreativeId);

    void deleteByCacheId(String cacheCreativeId);

    CreativeDTO findByObjId(String obj);

    void updateByObjId(CreativeDTO creativeEntity);

    void update(CreativeDTO newCreativeEntity, CreativeDTO creativeBackUpEntity);

    void delBack(Long oid);

    CreativeDTO getAllsBySomeParams(Map<String, Object> params);
}
