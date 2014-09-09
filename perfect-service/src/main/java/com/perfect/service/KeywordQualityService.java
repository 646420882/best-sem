package com.perfect.service;

import com.perfect.autosdk.sms.v3.Quality10Type;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-08-16.
 */
public interface KeywordQualityService {

    /**
     * @param redisKey
     * @param fieldName
     * @param n
     * @param skip
     * @param sort
     * @return
     */
    Map<String, Object> find(String redisKey, String fieldName, int n, int skip, int sort);

    /**
     * @param keywordIds
     * @return
     */
    List<Quality10Type> getKeyword10Quality(List<Long> keywordIds);

    /**
     * 下载关键词质量度报告
     *
     * @param redisKey
     * @param os
     */
    void downloadQualityCSV(String redisKey, OutputStream os);
}
