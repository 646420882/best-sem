package com.perfect.service;

import com.perfect.dto.CampaignTreeDTO;
import com.perfect.dto.KeywordDTO;
import com.perfect.entity.AdgroupEntity;
import com.perfect.entity.CampaignEntity;
import com.perfect.entity.KeywordEntity;
import com.perfect.mongodb.utils.PagerInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by john on 2014/8/19.
 */
public interface AssistantKeywordService {
    PagerInfo getKeyWords(String cid,String aid,Integer nowPage,Integer pageSize);

    void deleteByKwIds(List<String> kwids);

    KeywordEntity updateKeyword( KeywordEntity keywordEntity);

    Map<String,Object> validateDeleteByInput(Long accountId,String deleteInfos);

    Map<String,Object> validateDeleteKeywordByChoose(Long accountId,String chooseInfos, String keywordNames,Integer nowPage,Integer pageSize);

    List<CampaignTreeDTO> getCampaignTree(Long accountId);

    Map<String,Object> batchAddOrUpdateKeywordByChoose(Long accountId, Boolean isReplace, String chooseInfos, String keywordInfos);

    void batchAddUpdateKeyword(List<KeywordDTO> insertDtos, List<KeywordDTO> updateDtos, Boolean isReplace);

    Iterable<CampaignEntity> getCampaignByAccountId();

    Iterable<AdgroupEntity> getAdgroupByCid(String cid);

    void saveSearchwordKeyword(List<KeywordEntity> list);

    void setNeigWord(String agid, String keywords, Integer neigType);

    List<KeywordDTO> getKeywordListByIds(List<Long> ids);
}
