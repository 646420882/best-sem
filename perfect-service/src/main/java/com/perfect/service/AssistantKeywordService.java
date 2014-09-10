package com.perfect.service;

import com.perfect.dto.CampaignTreeDTO;
import com.perfect.entity.KeywordEntity;
import com.perfect.mongodb.utils.PagerInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by john on 2014/8/19.
 */
public interface AssistantKeywordService {
    PagerInfo getKeyWords(String cid,String aid,Integer nowPage);

    void deleteByKwIds(List<String> kwids);

    void updateKeyword( KeywordEntity keywordEntity);

    Map<String,Object> validateDeleteByInput(Long accountId,String deleteInfos);

    Map<String,Object> validateDeleteKeywordByChoose(Long accountId,String chooseInfos, String keywordNames);

    List<CampaignTreeDTO> getCampaignTree(Long accountId);

    void batchAddOrUpdateKeywordByInput(Long accountId, Boolean isReplace, String keywordInfos);

    Map<String,Object> batchAddOrUpdateKeywordByChoose(Long accountId, Boolean isReplace, String chooseInfos, String keywordInfos);
}
