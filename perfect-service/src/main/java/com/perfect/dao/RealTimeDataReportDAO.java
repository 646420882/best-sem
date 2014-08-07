package com.perfect.dao;

import com.perfect.entity.AdgroupRealTimeDataEntity;
import com.perfect.entity.CreativeRealTimeDataEntity;
import com.perfect.entity.KeywordRealTimeDataEntity;
import com.perfect.entity.RegionRealTimeDataEntity;

import java.util.List;

/**
 * Created by baizz on 2014-08-07.
 */
public interface RealTimeDataReportDAO {

    List<AdgroupRealTimeDataEntity> getAdgroupRealTimeData();

    List<CreativeRealTimeDataEntity> getCreativeRealTimeData();

    List<KeywordRealTimeDataEntity> getKeywordRealTimeData();

    List<RegionRealTimeDataEntity> getRegionRealTimeData();
}
