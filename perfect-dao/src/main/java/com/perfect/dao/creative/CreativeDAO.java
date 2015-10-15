package com.perfect.dao.creative;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.creative.CreativeDTO;
import com.perfect.utils.paging.PagerInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-7-10.
 */
public interface CreativeDAO extends HeyCrudRepository<CreativeDTO, Long> {

    List<Long> getCreativeIdByAdgroupId(Long adgroupId);

    List<CreativeDTO> findByAgroupId(Long adgroupId);

    List<CreativeDTO> getCreativeByAdgroupId(Long adgroupId, Map<String, Object> params, int skip, int limit);

    List<CreativeDTO> getCreativeByAdgroupId(String adgroupId, Map<String, Object> params, int skip, int limit);

    List<CreativeDTO> getAllsByAdgroupIds(List<Long> l);

    List<CreativeDTO> getAllsByAdgroupIdsForString(List<String> l);

    List<CreativeDTO> findHasLocalStatus();

    List<CreativeDTO> findHasLocalStatusStr(List<String> strs);

    List<CreativeDTO> findHasLocalStatusLong(List<Long> longs);

    /**
     * <p>获取指定百度账号下在本地新增 修改 删除的创意
     * type: 1 -> 新增, 2 -> 修改, 3 -> 删除</p>
     *
     * @param baiduAccountId
     * @return
     */
    List<CreativeDTO> findLocalChangedCreative(Long baiduAccountId, int type);

    void deleteByCacheId(Long cacheCreativeId);

    void deleteByCacheId(String cacheCreativeId);

    String insertOutId(CreativeDTO creativeEntity);

    void insertByReBack(CreativeDTO oldcreativeEntity);

    void insertAll(List<CreativeDTO> list);

    CreativeDTO findByObjId(String obj);

    CreativeDTO getAllsBySomeParams(Map<String, Object> params);

    void updateByObjId(CreativeDTO creativeEntity);

    void update(CreativeDTO newCreativeEntity, CreativeDTO creativeBackUpEntity);

    void updateAdgroupIdByOid(String id, Long adgroupId);

    void update(CreativeDTO creativeDTO);

    void delBack(Long oid);

    PagerInfo findByPagerInfo(Map<String, Object> params, Integer nowPage, Integer pageSize);

    PagerInfo findByPagerInfoForString(List<String> l, Integer nowPage, Integer pageSize);

    PagerInfo findByPagerInfoForLong(List<Long> l, Integer nowPage, Integer pageSize);

    PagerInfo findByPagerInfo(Long l, Integer nowPage, Integer pageSize);

    void update(String crid, CreativeDTO dto);

    void deleteByLongId(Long crid);

    void updateLs(Long crid, CreativeDTO dto);
}
