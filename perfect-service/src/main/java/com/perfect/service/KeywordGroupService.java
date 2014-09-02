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
     * @param status
     * @return
     */
    Map<String, Object> getKeywordFromPerfect(String trade, String category, int skip, int limit, int status);

    /**
     * 获取百度词库CSV文件下载路径
     *
     * @param krFileId
     * @return
     */
    Map<String, Object> getBaiduCSVFilePath(String krFileId);

    /**
     * CSV文件下载
     *
     * @param trade
     * @param category
     * @param os
     */
    void downloadCSV(String trade, String category, OutputStream os);

    /**
     * 查询行业词库下的类别
     *
     * @param trade
     * @return
     */
    Map<String, Object> findCategories(String trade);

    /**
     * save
     *
     * @param seedWordList
     * @param krFileId
     */
    void saveKeywordFromBaidu(List<String> seedWordList, String krFileId, String newCampaignName);

    /**
     * save
     *
     * @param trade
     * @param category
     */
    void saveKeywordFromPerfect(String trade, String category, String newCampaignName);
}
