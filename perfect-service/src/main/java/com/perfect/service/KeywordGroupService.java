package com.perfect.service;


import com.perfect.utils.paging.PagerInfo;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created on 2014-08-09.
 *
 * @author dolphineor
 * @update 2015-09-28
 */
public interface KeywordGroupService {

    String KR_FIELD_ID = "krFileId";
    String RESULT_TOTAL = "total";
    String LEXICON_TRADE = "tr";
    String LEXICON_CATEGORY = "cg";
    String LEXICON_GROUP = "gr";


    /**
     * <br>-----------------------</br>
     *
     * @param seedWordList
     * @param skip
     * @param limit
     * @param sort
     * @param fieldName
     * @return
     */
    Map<String, Object> getKRResult(List<String> seedWordList, int skip, int limit, int sort, String fieldName);

    /**
     * 从凤巢获取关键词
     *
     * @param seedWordList
     * @param skip
     * @param limit
     * @param reportId
     * @param sort
     * @param fieldName
     * @return
     */
    Map<String, Object> getKeywordFromBaidu(List<String> seedWordList, int skip, int limit, String reportId, int sort, String fieldName);

    /**
     * 从系统词库获取关键词
     *
     * @param trade
     * @param categories
     * @param groups
     * @param skip
     * @param limit
     * @param status
     * @return
     */
    Map<String, Object> getKeywordFromSystem(String trade, List<String> categories, List<String> groups, int skip, int limit, int status);

    /**
     * CSV文件下载
     *
     * @param trade
     * @param categories
     * @param groups
     * @param os
     */
    void downloadCSV(String trade, List<String> categories, List<String> groups, OutputStream os);

    /**
     * 凤巢CSV文件下载
     *
     * @param seedWordList
     * @param krFileId
     * @param os
     */
    void downloadBaiduCSV(List<String> seedWordList, String krFileId, OutputStream os);

    /**
     * 查询行业词库下的类别
     *
     * @param trade
     * @return
     */
    Map<String, Object> findCategories(String trade);


    /**
     * 查询某个类别下具体的关键词信息
     *
     * @param categories
     * @return
     */
    Map<String, Object> findKeywordByCategories(List<String> categories);

    /**
     * save
     *
     * @param seedWordList
     * @param krFileId
     */
//    void saveKeywordFromBaidu(List<String> seedWordList, String krFileId, String newCampaignName);

    /**
     * save
     *
     * @param trade
     * @param category
     */
//    void saveKeywordFromSystem(String trade, String category, String newCampaignName);

    /**
     * @param seedWord
     * @return
     */
    Map<String, Object> getKRbySeedWord(String seedWord);

    /**
     * 查询所有行业名
     *
     * @return
     */
    Map<String, Object> findTr();

    /**
     * 添加数据
     *
     * @param
     */
    int saveTrade(String tr, String cg, String gr, String kw, String url);

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @param page   当前页码
     * @param limit  每页最大条数
     * @return
     */
    PagerInfo findByPager(Map<String, Object> params, int page, int limit);

    /**
     * 根据行业，关键词删除一条数据
     *
     * @param trade
     * @param keyword
     */
    void deleteByParams(String trade, String keyword);

    /**
     * 根据一些参数修改
     *
     * @param mapParams
     */
    void updateByParams(Map<String, Object> mapParams);
}
