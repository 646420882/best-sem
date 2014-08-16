package com.perfect.service;

import java.util.Map;

/**
 * Created by baizz on 2014-08-16.
 */
public interface KeywordQualityService {

    /**
     *
     * @param startDate
     * @param endDate
     * @param fieldName
     * @param n
     * @param sort
     * @return
     */
    Map<String, Object> find(String startDate, String endDate, String fieldName, int n, int sort);
}
