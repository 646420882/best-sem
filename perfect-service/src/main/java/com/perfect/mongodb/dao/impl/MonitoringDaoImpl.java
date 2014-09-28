package com.perfect.mongodb.dao.impl;

import com.perfect.core.AppContext;
import com.perfect.dao.MonitoringDao;
import com.perfect.entity.FolderEntity;
import com.perfect.entity.FolderMonitorEntity;
import com.perfect.mongodb.base.BaseMongoTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import static com.perfect.mongodb.utils.EntityConstants.*;
import java.util.List;

/**
 * Created by SubDong on 2014/9/15.
 */
@Repository("monitoringDao")
public class MonitoringDaoImpl implements MonitoringDao{
    @Override
    public List<FolderEntity> getForlder() {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        List<FolderEntity> forlderEntities = mongoTemplate.find(new Query(),FolderEntity.class,TBL_MONITORING_FOLDERS+"_"+ AppContext.getAccountId());
        return forlderEntities;
    }

    @Override
    public List<FolderMonitorEntity> getMonitor() {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        List<FolderMonitorEntity> entities = mongoTemplate.find(new Query(),FolderMonitorEntity.class,TBL_MONITORING_TARGETS+"_"+ AppContext.getAccountId());
        return entities;
    }

    @Override
    public List<FolderMonitorEntity> getMonitor(Long forlderId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        List<FolderMonitorEntity> entities = mongoTemplate.find(Query.query(Criteria.where(FOLDER_ID).is(forlderId)),FolderMonitorEntity.class,TBL_MONITORING_TARGETS+"_"+ AppContext.getAccountId());
        return entities;
    }
}
