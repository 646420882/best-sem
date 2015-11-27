/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/
package com.perfect.dao.adgroup;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.param.SearchFilterParam;
import com.perfect.utils.paging.PagerInfo;

import java.util.List;
import java.util.Map;

public interface AdgroupDAO extends HeyCrudRepository<AdgroupDTO, Long> {

    List<Long> getAllAdgroupId();

    List<String> getAllAdgroupIdStr();

    Map<Long, Long> getAllAdgroupIdByBaiduAccountId(Long baiduAccoutId);

    List<Long> getAdgroupIdByCampaignId(Long campaignId);

    List<Long> getAdgroupIdByCampaignObj(String campaignId);

    List<String> getAdgroupIdByCampaignId(String campaignId);

    List<AdgroupDTO> getAdgroupByCampaignId(Long campaignId, Map<String, Object> params, int skip, int limit);

    List<AdgroupDTO> getAdgroupByCampaignObjId(String campaignObjId);

    List<AdgroupDTO> getAdgroupByCampaignId(Long campaignId);

    List<AdgroupDTO> findHasLocalStatus();

    List<AdgroupDTO> findHasLocalStatusStr(List<String> str);

    List<AdgroupDTO> findHasLocalStatusLong(List<Long> longs);

    List<AdgroupDTO> findDownloadAdgroup(Long baiduAccountId, List<Long> adgroupIds);

    /**
     * <p>获取指定百度账号下在本地新增 修改 删除的推广单元
     * type: 1 -> 新增, 2 -> 修改, 3 -> 删除</p>
     *
     * @param baiduAccountId
     * @return
     */
    List<AdgroupDTO> findLocalChangedAdgroups(Long baiduAccountId, int type);

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

    PagerInfo findByPagerInfo(Map<String, Object> params, Integer nowPage, Integer pageSize, SearchFilterParam sp);

    AdgroupDTO getByCampaignIdAndName(Long campaignId, String name);

    List<AdgroupDTO> findByCampaignOId(String id);

    List<String> getObjAdgroupIdByCampaignId(List<String> cids);

    void deleteLinkedByAgid(List<Long> agid);

    void insert(AdgroupDTO adgroupDTO);

    List<AdgroupDTO> findByTwoParams(Long cid, Long accountId);

    double findPriceRatio(Long cid);

    void update(String oid, AdgroupDTO dto);

    void deleteBubLinks(Long aid);

    void pdateUpdate(Long aid, AdgroupDTO dto);

    double getCampBgt(String cid);

    double getCampBgt(Long cid);

    /**
     * 批量删除
     *
     * @param asList
     * @param keywordDatas
     * @param creativeDatas
     */
    void batchDelete(List<String> asList, List<String> keywordDatas, List<String> creativeDatas);

    /**
     * 批量启用/暂停 单元
     *
     * @param strings
     * @param status
     */
    void enableOrPauseAdgroup(List<String> strings, boolean status);

    /**
     * 通过计划ID查询单元ID  百度ID
     * @param campaignId
     * @return
     */
    List<Long> getAdgroupIdByCampaignIdListLong(List<Long> campaignId);

    /**
     * 通过计划ID查询单元ID  本地ID
     * @param campaignId
     * @return
     */
    List<String> getAdgroupIdByCampaignIdListString(List<String> campaignId);

    AdgroupDTO findByAdgroupName(String adgroupName);
}