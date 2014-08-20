package com.perfect.service;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-08-09.
 */
public interface KeywordGroupService {

    /**
     * <br>-----------------------</br>
     *
     * @param seedWordList
     * @return
     */
    Map<String, Object> getKRResult(List<String> seedWordList, int skip, int limit);

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
     * 从系统词库获取关键词
     *
     * @param trade
     * @param category
     * @param skip
     * @param limit
     * @return
     */
    Map<String, Object> getKeywordFromPerfect(String trade, String category, int skip, int limit);

    void downloadCSV(String trade, String category, OutputStream os);
}
