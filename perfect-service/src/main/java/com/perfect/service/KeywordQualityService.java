package com.perfect.service;

import com.perfect.autosdk.sms.v3.Quality10Type;

import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-08-16.
 */
public interface KeywordQualityService {

    /**
     * @param fieldName
     * @param n
     * @param skip
     * @param sort
     * @return
     */
    Map<String, Object> find(String fieldName, int n, int skip, int sort);

    /**
     * @param keywordIds
     * @return
     */
    List<Quality10Type> getKeyword10Quality(List<Long> keywordIds);
}
