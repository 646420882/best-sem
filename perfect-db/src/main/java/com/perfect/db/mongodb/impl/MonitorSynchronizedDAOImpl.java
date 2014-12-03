package com.perfect.db.mongodb.impl;

import com.perfect.core.AppContext;
import com.perfect.dao.monitoring.MonitorSynchronizedDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import com.perfect.dto.monitor.FolderDTO;
import com.perfect.dto.monitor.FolderMonitorDTO;
import com.perfect.entity.FolderEntity;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by SubDong on 2014/9/12.
 */
@Repository("monitorSynchronizedDAO")
public class MonitorSynchronizedDAOImpl extends AbstractUserBaseDAOImpl<FolderDTO,Long> implements MonitorSynchronizedDAO {

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

    @Override
    public Class<FolderEntity> getEntityClass() {
        return FolderEntity.class;
    }

    @Override
    public Class<FolderDTO> getDTOClass() {
        return FolderDTO.class;
    }
}
