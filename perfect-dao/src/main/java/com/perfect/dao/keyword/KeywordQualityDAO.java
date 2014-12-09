package com.perfect.dao.keyword;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.keyword.KeywordReportDTO;

import java.util.List;

/**
 * Created by baizz on 2014-07-28.
 * 2014-12-2 refactor
 */
public interface KeywordQualityDAO extends HeyCrudRepository<KeywordReportDTO, Long> {

    List<KeywordReportDTO> findYesterdayKeywordReport();

}
