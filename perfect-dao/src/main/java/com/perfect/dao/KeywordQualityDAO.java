package com.perfect.dao;

import com.perfect.dto.keyword.KeywordReportDTO;

import java.util.List;

/**
 * Created by baizz on 2014-07-28.
 */
public interface KeywordQualityDAO {

    List<KeywordReportDTO> findYesterdayKeywordReport();

//    @Deprecated
//    List<Long> findYesterdayAllKeywordId();
}
