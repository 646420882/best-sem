package com.perfect.service;

import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-08-09.
 */
public interface KeywordGroupService {

    Map<String, Object> getKRResult(String seedWord);

    Map<String, Object> getKRResult(List<String> seedWordList);

    /**
     * 从百度词库获取关键词
     *
     * @param seedWordList
     * @param skip
     * @param limit
     * @param reportId
     * @return
     */
    Map<String, Object> getKeywordFromBaidu(List<String> seedWordList, int skip, int limit, String reportId);

    /**
     * 基于百度的词库分组
     *
     * @param words
     * @return
     */
    Map<String, Object> autoGroupByBaidu(List<String> words);

    void addKeywords(String jsonArrays);
}
