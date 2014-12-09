package com.perfect.db.mongodb.impl;

import com.perfect.core.AppContext;
import com.perfect.dao.monitoring.MonitorSynchronizedDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import com.perfect.dto.monitor.FolderDTO;
import com.perfect.dto.monitor.FolderMonitorDTO;
import com.perfect.entity.FolderEntity;
import com.perfect.entity.FolderMonitorEntity;
import com.perfect.utils.ObjectUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
        List<FolderEntity> entities = ObjectUtils.convert(forlderEntities, FolderEntity.class);
        int i = -1;
        List<FolderEntity> folderEntities = mongoTemplate.find(Query.query(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId())), FolderEntity.class, TBL_MONITORING_FOLDERS);
        try {
            if(folderEntities.size() > 0){
                mongoTemplate.remove(Query.query(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId())), TBL_MONITORING_FOLDERS);
                mongoTemplate.insert(entities, TBL_MONITORING_FOLDERS);
            }else{
                mongoTemplate.insert(entities, TBL_MONITORING_FOLDERS);
            }
            i = 1;
        } catch (Exception e) {
            i = -1;
        }
        return i;
    }

    @Override
    public int insterMoniterData(List<FolderMonitorDTO> folderMonitorEntities) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        List<FolderMonitorEntity> entityList = ObjectUtils.convert(folderMonitorEntities, FolderMonitorEntity.class);
        int i = -1;
        try {
        List<FolderMonitorEntity> monitorEntities = mongoTemplate.find(Query.query(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId())), FolderMonitorEntity.class, TBL_MONITORING_TARGETS);
            if(monitorEntities.size() > 0){
                mongoTemplate.remove(Query.query(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId())), TBL_MONITORING_TARGETS);
                mongoTemplate.insert(entityList, TBL_MONITORING_TARGETS);
            }else{
                mongoTemplate.insert(entityList, TBL_MONITORING_TARGETS);
            }
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
