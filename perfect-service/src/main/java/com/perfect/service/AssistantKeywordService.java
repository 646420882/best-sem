package com.perfect.service;

import com.perfect.dto.CampaignTreeDTO;
import com.perfect.entity.KeywordEntity;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Map;

/**
 * Created by john on 2014/8/19.
 */
public interface AssistantKeywordService {
    Iterable<KeywordEntity> getKeyWords(Query query);

    void deleteByKwIds(List<Long> kwids);

    void updateKeyword( KeywordEntity keywordEntity);

    Map<String,Object> validateDeleteByInput(Long accountId,String deleteInfos);

    void deleteKeywordByNamesChoose(Long accountId,String chooseInfos, String keywordNames);

    List<CampaignTreeDTO> getCampaignTree(Long accountId);

    void batchAddOrUpdateKeywordByInput(Long accountId, Boolean isReplace, String keywordInfos);

    void batchAddOrUpdateKeywordByChoose(Long accountId, Boolean isReplace, String chooseInfos, String keywordInfos);
}
