package com.perfect.service;

import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.creative.CreativeDTO;
import com.perfect.param.FindOrReplaceParam;
import com.perfect.param.SearchFilterParam;
import com.perfect.utils.paging.PagerInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by SubDong on 2014/11/26.
 */
public interface CreativeService {

    public List<Long> getCreativeIdByAdgroupId(Long adgroupId);

    public List<CreativeDTO> getCreativeByAdgroupId(Long adgroupId, Map<String, Object> params, int skip, int limit);

    public Iterable<CreativeDTO> findAll();

    public List<CreativeDTO> findHasLocalStatus();

    public List<CreativeDTO> findHasLocalStatusStr(List<AdgroupDTO> adgroupDTOStr);

    public List<CreativeDTO> findHasLocalStatusLong(List<AdgroupDTO> adgroupDTOLong);

    public CreativeDTO findOne(Long creativeId);

    public List<CreativeDTO> find(Map<String, Object> params, int skip, int limit);

    void insertAll(List<CreativeDTO> list);

    void update(CreativeDTO creativeDTO);

    void updateCreative(CreativeDTO creativeDTO);

    void delete(Long creativeId);

    void deleteByIds(List<Long> creativeIds);

    PagerInfo findByPagerInfo(Map<String, Object> map, int nowPage, int pageSize,SearchFilterParam sp);

    PagerInfo findByPagerInfo(Long l, Integer nowPage, Integer pageSize,SearchFilterParam sp);

    PagerInfo findByPagerInfoForLong(List<Long> longs, int nowpage, int pageSize,SearchFilterParam sp);

    String insertOutId(CreativeDTO creativeEntity);

    void deleteByCacheId(Long cacheCreativeId);

    void deleteByCacheId(String cacheCreativeId);

    CreativeDTO findByObjId(String obj);

    void updateByObjId(CreativeDTO creativeEntity);

    void update(CreativeDTO newCreativeEntity, CreativeDTO creativeBackUpEntity);

    void delBack(Long oid);

    CreativeDTO getAllsBySomeParams(Map<String, Object> params);


    /**
     * 上传添加操作到百度
     *
     * @param crid 本地的mongodb Id
     * @return返回的创意百度ID
     */
    List<CreativeDTO> uploadAdd(List<String> crid);

    /**
     * 上传创意时用户选择级联上传
     *
     * @param crid 要上传的创意
     * @return 返回创建成功的创意, 内含创意id和Status
     */
    List<CreativeDTO> uploadAddByUp(String crid);

    /**
     * 上传成功后修改本地的状态
     *
     * @param crid 要修改的crid
     * @param dto  设定一些从百度返回回来的状态，Status
     */
    void update(String crid, CreativeDTO dto);

    /**
     * 上传删除操作到百度
     *
     * @param crid
     * @return
     */
    Integer uploadDel(Long crid);

    /**
     * 根据百度crid删除本地信息
     *
     * @param crid
     */
    void deleteByLongId(Long crid);

    /**
     * 上传修改操作到百度
     *
     * @param crids 需要上传修改的ids列表
     * @return 返回修改成功后的对象，对象中只包含修改成功后的创意id和status
     */
    List<CreativeDTO> uploadUpdate(List<Long> crids);

    /**
     * 修改成功后，修改该条记录的本地状态与Stauts状态，
     *
     * @param crid 要修改的crids
     * @param dto  获取status的对象
     */
    void updateLs(Long crid, CreativeDTO dto);

    List<CreativeDTO> getByCampaignIdStr(String cid);

    List<CreativeDTO> getByCampaignIdLong(Long cid);

    /**
     * 批量删除关键字
     * @param param
     */
    void batchDelete(FindOrReplaceParam param);

    void cut(CreativeDTO dto,String aid);
}
