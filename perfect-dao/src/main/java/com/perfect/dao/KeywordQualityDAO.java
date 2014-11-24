package com.perfect.dao;

import com.perfect.entity.KeywordReportEntity;

import java.util.List;

/**
 * Created by baizz on 2014-07-28.
 */
public interface KeywordQualityDAO {

    List<KeywordReportEntity> findYesterdayKeywordReport();

    @Deprecated
    List<Long> findYesterdayAllKeywordId();
}
