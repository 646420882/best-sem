package com.perfect.mongodb.dao.impl;

import com.perfect.dao.MonitorSynchronizedDAO;
import com.perfect.entity.FolderEntity;
import com.perfect.entity.FolderMonitorEntity;
import com.perfect.mongodb.base.BaseMongoTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import static com.perfect.mongodb.utils.EntityConstants.*;
import java.util.List;

/**
 * Created by SubDong on 2014/9/12.
 */
@Repository("monitorSynchronizedDAO")
public class MonitorSynchronizedDAOImpl implements MonitorSynchronizedDAO {

    @Override
    public int insterData(List<FolderEntity> forlderEntities) {

        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        int i = -1;
        boolean verify = mongoTemplate.getDb().collectionExists(TBL_MONITORING_FOLDERS);
        if (verify) {
            mongoTemplate.dropCollection(TBL_MONITORING_FOLDERS);
        }
        try {
            mongoTemplate.insert(forlderEntities, TBL_MONITORING_FOLDERS);
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
        boolean verify = mongoTemplate.getDb().collectionExists(TBL_MONITORING_TARGETS);
        if (verify) {
            mongoTemplate.dropCollection(TBL_MONITORING_TARGETS);
        }
        try {
            mongoTemplate.insert(folderMonitorEntities, TBL_MONITORING_TARGETS);
            i = 1;
        } catch (Exception e) {
            i = -1;
        }


        return i;
    }
}
