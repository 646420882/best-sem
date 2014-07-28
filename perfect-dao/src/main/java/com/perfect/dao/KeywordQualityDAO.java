package com.perfect.dao;

import com.perfect.entity.KeywordRealTimeDataVOEntity;

/**
 * Created by baizz on 14-7-28.
 */
public interface KeywordQualityDAO {

    /**
     * 关键词质量度查询
     *
     * @param _startDate
     * @param _endDate
     * @param fieldName
     * @param limit
     * @return
     */
    KeywordRealTimeDataVOEntity[] find(String _startDate, String _endDate, String fieldName, int limit);
}
