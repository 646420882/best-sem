package com.perfect.service;

import com.perfect.entity.CampaignTreeVoEntity;
import com.perfect.entity.KeywordEntity;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * Created by john on 2014/8/19.
 */
public interface AssistantKeywordService {
    Iterable<KeywordEntity> getAllKeyWord(Long accountId);

    void deleteByKwIds(List<Long> kwids);

    void updateKeyword( KeywordEntity keywordEntity);

    List findByQuery(Query query);

    void deleteKeywordByNamesInput(Long accountId,String deleteInfos);

    void deleteKeywordByNamesChoose(Long accountId,String chooseInfos, String keywordNames);

    List<CampaignTreeVoEntity> getCampaignTree(Long accountId);

    void batchAddOrUpdateKeywordByInput(Long accountId, Boolean isReplace, String keywordInfos);
}
