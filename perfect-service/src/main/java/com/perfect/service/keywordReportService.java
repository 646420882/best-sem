package com.perfect.service;

import com.perfect.dao.MongoCrudRepository;
import com.perfect.entity.KeywordReportEntity;
import com.perfect.mongodb.utils.PagerInfo;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/9/17.
 */
public interface KeywordReportService extends MongoCrudRepository<KeywordReportEntity,Long>{
    PagerInfo findByPagerInfo(Map<String,Object> params);
}
