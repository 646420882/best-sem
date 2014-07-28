package com.perfect.dao;

import com.perfect.entity.KeywordRealTimeDataVOEntity;

/**
 * Created by baizz on 14-7-28.
 */
public interface KeywordQualityDAO {
    public KeywordRealTimeDataVOEntity[] find(String _startDate, String _endDate, String fieldName, int limit);



    
}
