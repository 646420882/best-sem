package com.perfect.dao;


import com.perfect.dto.keyword.KeywordReportDTO;
import com.perfect.utils.PagerInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/9/17.
 */
public interface KeywordReportDAO extends MongoCrudRepository<KeywordReportDTO,Long> {
    PagerInfo findByPagerInfo(Map<String, Object> params);
    List<KeywordReportDTO> getAll(Map<String,Object> params);
}
