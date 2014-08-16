package com.perfect.dao;

import com.perfect.entity.KeywordReportEntity;

import java.util.List;

/**
 * Created by baizz on 2014-07-28.
 */
public interface KeywordQualityDAO {

    /**
     *
     * @param _startDate
     * @param _endDate
     * @return
     */
    List<KeywordReportEntity> find(String _startDate, String _endDate);
}
