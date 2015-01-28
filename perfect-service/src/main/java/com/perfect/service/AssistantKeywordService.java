package com.perfect.service;

import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.backup.KeywordBackUpDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.dto.campaign.CampaignTreeDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.dto.keyword.KeywordInfoDTO;
import com.perfect.utils.paging.PagerInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by john on 2014/8/19.
 */
public interface AssistantKeywordService {
    PagerInfo getKeyWords(String cid,String aid,Integer nowPage,Integer pageSize);

    void deleteByKwIds(List<String> kwids);

    KeywordDTO updateKeyword( KeywordDTO keywordDTO);

    Map<String,Object> validateDeleteByInput(Long accountId,String deleteInfos);

    Map<String,Object> validateDeleteKeywordByChoose(Long accountId,String chooseInfos, String keywordNames,Integer nowPage,Integer pageSize);

    List<CampaignTreeDTO> getCampaignTree(Long accountId);

    Iterable<KeywordDTO> findAll();

    List<KeywordDTO> findHasLocalStatus();

    Map<String,Object> batchAddOrUpdateKeywordByChoose(Long accountId, Boolean isReplace, String chooseInfos, String keywordInfos);

    void batchAddUpdateKeyword(List<KeywordInfoDTO> insertDtos, List<KeywordInfoDTO> updateDtos, Boolean isReplace);

    Iterable<CampaignDTO> getCampaignByAccountId();

    Iterable<AdgroupDTO> getAdgroupByCid(String cid);

    void saveSearchwordKeyword(List<KeywordDTO> list);

    void setNeigWord(String agid, String keywords, Integer neigType);

    List<KeywordInfoDTO> getKeywordListByIds(List<Long> ids);

    KeywordDTO findByParams(Map<String,Object> params);

    KeywordDTO findByObjId(String obj);

    KeywordDTO findByLongId(Long id);

    void updateByObjId(KeywordDTO dto);

    void update(KeywordDTO keywordDTO,KeywordDTO keywordBackUpDTO);

    void insert(KeywordDTO dto);

    /**
     * 上传添加操作，获取到kid以及status
     * @param kids 获取到的百度kid
     * @return
     */
    List<KeywordDTO> uploadAdd(List<String> kids);

    /**
     * 上传添加级联操作，如果用户选择级联上传，则上传关键词 的单元以及计划
     * @param kid 要上传的关键词
     * @return 返回成功后的关键字，包括id和Status
     */
    List<KeywordDTO> uploadAddByUp(String kid);

    /**
     *  更新成功后需要将本地的关键字的Kwid 更新出来，然后将statu改为百度的status，ls改为null
     * @param dto 获取到的百度对象
     * @param oid 本地的mongodbId，用于查询
     */
    void update(String oid,KeywordDTO dto);

    /**
     * 上传删除操作，
     * @param kid 要删除的关键词id
     * @return 删除成功返回null值，以此判断删除是否成功
     */
    Integer uploadDel(Long kid);

    /**
     * 上传修改操作
     * @param kid 要修改的kid
     * @return 修改过后的KeywordDTO
     */
    List<KeywordDTO> uploadUpdate(List<Long> kid);

}
