package com.perfect.db.mongodb.impl;

import com.perfect.core.AppContext;
import com.perfect.dao.MonitorSynchronizedDAO;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import com.perfect.dto.monitor.FolderDTO;
import com.perfect.dto.monitor.FolderMonitorDTO;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.perfect.commons.constants.MongoEntityConstants.TBL_MONITORING_FOLDERS;
import static com.perfect.commons.constants.MongoEntityConstants.TBL_MONITORING_TARGETS;

/**
 * Created by SubDong on 2014/9/12.
 */
@Repository("monitorSynchronizedDAO")
public class MonitorSynchronizedDAOImpl implements MonitorSynchronizedDAO {

    @Override
    public int insterData(List<FolderDTO> forlderEntities) {

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
    public int insterMoniterData(List<FolderMonitorDTO> folderMonitorEntities) {
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
