package com.perfect.dao;

import com.perfect.entity.KeywordReportEntity;
import com.perfect.mongodb.utils.PagerInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/9/17.
 */
public interface KeywordReportDAO extends MongoCrudRepository<KeywordReportEntity,Long> {
    PagerInfo findByPagerInfo(Map<String, Object> params);
    List<KeywordReportEntity> getAll(Map<String,Object> params);
}
