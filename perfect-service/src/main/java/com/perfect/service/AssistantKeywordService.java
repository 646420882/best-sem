package com.perfect.service;

import com.perfect.entity.KeywordEntity;

import java.util.List;

/**
 * Created by john on 2014/8/19.
 */
public interface AssistantKeywordService {
    Iterable<KeywordEntity> getAllKeyWord();

    void deleteByKwIds(List<Long> kwids);

    void updateKeyword( KeywordEntity keywordEntity);
}
