package com.perfect.dao.mongodb.impl;

import com.perfect.core.AppContext;
import com.perfect.dao.MonitorSynchronizedDAO;
import com.perfect.entity.FolderEntity;
import com.perfect.entity.FolderMonitorEntity;
import com.perfect.dao.mongodb.base.BaseMongoTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.perfect.commons.constants.MongoEntityConstants.*;

/**
 * Created by SubDong on 2014/9/12.
 */
@Repository("monitorSynchronizedDAO")
public class MonitorSynchronizedDAOImpl implements MonitorSynchronizedDAO {

    @Override
    public int insterData(List<FolderEntity> forlderEntities) {

        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        int i = -1;
        boolean verify = mongoTemplate.getDb().collectionExists(TBL_MONITORING_FOLDERS + "_" + AppContext.getAccountId());
        if (verify) {
            mongoTemplate.dropCollection(TBL_MONITORING_FOLDERS + "_" + AppContext.getAccountId());
        }
        try {
            mongoTemplate.insert(forlderEntities, TBL_MONITORING_FOLDERS + "_" + AppContext.getAccountId());
            i = 1;
        } catch (Exception e) {
            i = -1;
        }


        return i;
    }

    @Override
    public int insterMoniterData(List<FolderMonitorEntity> folderMonitorEntities) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        int i = -1;
        boolean verify = mongoTemplate.getDb().collectionExists(TBL_MONITORING_TARGETS + "_" + AppContext.getAccountId());
        if (verify) {
            mongoTemplate.dropCollection(TBL_MONITORING_TARGETS + "_" + AppContext.getAccountId());
        }
        try {
            mongoTemplate.insert(folderMonitorEntities, TBL_MONITORING_TARGETS + "_" + AppContext.getAccountId());
            i = 1;
        } catch (Exception e) {
            i = -1;
        }


        return i;
    }
}
