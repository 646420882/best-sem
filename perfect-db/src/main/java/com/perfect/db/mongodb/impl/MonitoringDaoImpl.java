package com.perfect.db.mongodb.impl;

import com.mongodb.WriteResult;
import com.perfect.core.AppContext;
import com.perfect.dao.MonitoringDao;
import com.perfect.entity.FolderEntity;
import com.perfect.entity.FolderMonitorEntity;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.perfect.commons.constants.MongoEntityConstants.*;

/**
 * Created by SubDong on 2014/9/15.
 */
@Repository("monitoringDao")
public class MonitoringDaoImpl implements MonitoringDao {
    @Override
    public List<FolderEntity> getForlder() {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        List<FolderEntity> forlderEntities = mongoTemplate.find(new Query(), FolderEntity.class, TBL_MONITORING_FOLDERS + "_" + AppContext.getAccountId());
        return forlderEntities;
    }

    @Override
    public List<FolderEntity> getForlderId(Long folderId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        List<FolderEntity> forlderEntities = mongoTemplate.find(Query.query(Criteria.where(FOLDER_ID).is(folderId)), FolderEntity.class, TBL_MONITORING_FOLDERS + "_" + AppContext.getAccountId());
        return forlderEntities;
    }

    @Override
    public boolean updataForlderId(Long folderId, String folderName) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Update update = new Update();
        update.set("fdna", folderName);
        WriteResult writeResult = mongoTemplate.updateFirst(Query.query(Criteria.where(FOLDER_ID).is(folderId)), update, TBL_MONITORING_FOLDERS + "_" + AppContext.getAccountId());
        boolean b = writeResult.isUpdateOfExisting();
        return b;
    }

    @Override
    public void addFolder(FolderEntity folderEntity) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.insert(folderEntity, TBL_MONITORING_FOLDERS + "_" + AppContext.getAccountId());
    }

    @Override
    public WriteResult deleteFoder(Long folderId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        WriteResult remove = mongoTemplate.remove(Query.query(Criteria.where(FOLDER_ID).is(folderId)), TBL_MONITORING_FOLDERS + "_" + AppContext.getAccountId());
        return remove;
    }

    @Override
    public WriteResult deleteMonitor(Long folderId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        WriteResult remove = mongoTemplate.remove(Query.query(Criteria.where(FOLDER_ID).is(folderId)), TBL_MONITORING_TARGETS + "_" + AppContext.getAccountId());
        return remove;
    }

    @Override
    public List<FolderMonitorEntity> getMonitor() {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        List<FolderMonitorEntity> entities = mongoTemplate.find(new Query(), FolderMonitorEntity.class, TBL_MONITORING_TARGETS + "_" + AppContext.getAccountId());
        return entities;
    }

    @Override
    public List<FolderMonitorEntity> getMonitorId(Long folderId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        List<FolderMonitorEntity> entities = mongoTemplate.find(Query.query(Criteria.where(FOLDER_ID).is(folderId)), FolderMonitorEntity.class, TBL_MONITORING_TARGETS + "_" + AppContext.getAccountId());
        return entities;
    }

    @Override
    public WriteResult deleteMonitorId(Long MonitorId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        WriteResult remove = mongoTemplate.remove(Query.query(Criteria.where(MONITOR_ID).is(MonitorId)), TBL_MONITORING_TARGETS + "_" + AppContext.getAccountId());
        return remove;
    }

    @Override
    public void addMonitor(FolderMonitorEntity folderMonitorEntity) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.insert(folderMonitorEntity, TBL_MONITORING_TARGETS + "_" + AppContext.getAccountId());
    }

    @Override
    public Long getForlderCountByKwid(long kwid) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        return mongoTemplate.count(new Query(Criteria.where(MONITOR_ACLID).is(kwid)), TBL_MONITORING_TARGETS + "_" + AppContext.getAccountId());
    }
}
