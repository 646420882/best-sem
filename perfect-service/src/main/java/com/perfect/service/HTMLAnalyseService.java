package com.perfect.service;

import com.perfect.api.baidu.BaiduSpiderHelper;

import java.util.List;

/**
 * Created by baizz on 2014-08-18.
 */
public interface HTMLAnalyseService {
    /**
     * 关键词模糊匹配
     *
     * @param keyword
     * @param matchWord
     * @return
     */
    boolean vagueMatch(String keyword, String matchWord);

    /**
     * 从页面抓取推广数据
     *
     * @param getPreviewRequest
     * @return
     */
    List<BaiduSpiderHelper.PreviewData> getPageData(String[] keyword, Integer region);
}
