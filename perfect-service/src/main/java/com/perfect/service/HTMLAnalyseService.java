package com.perfect.service;

import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.sms.v3.GetPreviewRequest;
import com.perfect.entity.CreativeVOEntity;
import com.perfect.service.impl.HTMLAnalyseServiceImpl;

import java.util.List;
import java.util.Map;

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
    List<HTMLAnalyseServiceImpl.PreviewData> getPageData(GetPreviewRequest getPreviewRequest);
}
