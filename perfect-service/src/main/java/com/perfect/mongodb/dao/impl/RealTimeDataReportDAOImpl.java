package com.perfect.mongodb.dao.impl;

import com.perfect.core.AppContext;
import com.perfect.dao.RealTimeDataReportDAO;
import com.perfect.entity.AdgroupRealTimeDataEntity;
import com.perfect.entity.CreativeRealTimeDataEntity;
import com.perfect.entity.KeywordRealTimeDataEntity;
import com.perfect.entity.RegionRealTimeDataEntity;
import com.perfect.mongodb.utils.BaseMongoTemplate;
import com.perfect.utils.UserUtil;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by baizz on 2014-08-07.
 */
@Repository("realTimeDataReportDAO")
public class RealTimeDataReportDAOImpl implements RealTimeDataReportDAO {

    private MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate(
            UserUtil.getDatabaseName(AppContext.getUser().toString(), "report"));

    public List<AdgroupRealTimeDataEntity> getAdgroupRealTimeData() {
        return null;
    }

    public List<CreativeRealTimeDataEntity> getCreativeRealTimeData() {
        return null;
    }

    public List<KeywordRealTimeDataEntity> getKeywordRealTimeData() {
        return null;
    }

    public List<RegionRealTimeDataEntity> getRegionRealTimeData() {
        return null;
    }
}
