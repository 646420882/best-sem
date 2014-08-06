package com.perfect.dao;

import com.perfect.entity.KCRealTimeDataEntity;

/**
 * Created by baizz on 2014-07-28.
 */
public interface KeywordQualityDAO {

    KCRealTimeDataEntity[] find(String _startDate, String _endDate, String fieldName, int limit, int sort);
}
