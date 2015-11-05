package com.perfect.db.mongodb.impl;

import com.mongodb.WriteResult;
import com.perfect.core.AppContext;
import com.perfect.dao.monitoring.MonitoringDao;
import com.perfect.db.mongodb.base.AbstractSysBaseDAOImpl;
import com.perfect.dto.monitor.FolderDTO;
import com.perfect.dto.monitor.FolderMonitorDTO;
import com.perfect.entity.FolderEntity;
import com.perfect.entity.FolderMonitorEntity;
import com.perfect.utils.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by SubDong on 2014/9/15.
 */
@Repository("monitoringDao")
public class MonitoringDaoImpl extends AbstractSysBaseDAOImpl<FolderDTO, Long> implements MonitoringDao {
    @Override
    public List<FolderDTO> getForlder() {
        List<FolderEntity> forlderEntities = getMongoTemplate().find(Query.query(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId())), FolderEntity.class, TBL_MONITORING_FOLDERS);

        List<FolderDTO> folderDTOs = ObjectUtils.convert(forlderEntities, FolderDTO.class);

        return folderDTOs;
    }

    @Override
    public List<FolderDTO> getForlderId(Long folderId) {
        List<FolderEntity> forlderEntities = getMongoTemplate().find(Query.query(Criteria.where(FOLDER_ID).is(folderId).and(ACCOUNT_ID).is(AppContext.getAccountId())), FolderEntity.class, TBL_MONITORING_FOLDERS);

        List<FolderDTO> folderDTOs = ObjectUtils.convert(forlderEntities, FolderDTO.class);

        return folderDTOs;
    }

    @Override
    public boolean updataForlderId(Long folderId, String folderName) {
        FolderEntity folderEntity = getMongoTemplate().findOne(new Query(Criteria.where(FOLDER_ID).is(folderId).and(ACCOUNT_ID).is(AppContext.getAccountId())), FolderEntity.class, TBL_MONITORING_FOLDERS);
        Update update = new Update();
        update.set("fdna", folderName);
        if(folderEntity != null && folderEntity.getLocalStatus() == 0){
            update.set("ls", 5);
        }else{
            update.set("ls", 2);
        }
        WriteResult writeResult = getMongoTemplate().updateFirst(Query.query(Criteria.where(FOLDER_ID).is(folderId).and(ACCOUNT_ID).is(AppContext.getAccountId())), update, TBL_MONITORING_FOLDERS);
        boolean b = writeResult.isUpdateOfExisting();
        return b;
    }

    @Override
    public void addFolder(FolderDTO folderDTO) {
        FolderEntity folderEntity = new FolderEntity();
        BeanUtils.copyProperties(folderDTO, folderEntity);

        getMongoTemplate().insert(folderEntity, TBL_MONITORING_FOLDERS);
    }

    @Override
    public int deleteFoder(Long folderId) {
        FolderEntity folderEntity = getMongoTemplate().findOne(new Query(Criteria.where(FOLDER_ID).is(folderId).and(ACCOUNT_ID).is(AppContext.getAccountId())), FolderEntity.class, TBL_MONITORING_FOLDERS);
        WriteResult remove;
        if(folderEntity != null && folderEntity.getLocalStatus() == 0){
            Update update = new Update();
            update.set("ls",4);
            remove = getMongoTemplate().updateFirst(Query.query(Criteria.where(FOLDER_ID).is(folderId).and(ACCOUNT_ID).is(AppContext.getAccountId())), update, TBL_MONITORING_FOLDERS);
        }else{
            remove = getMongoTemplate().remove(Query.query(Criteria.where(FOLDER_ID).is(folderId).and(ACCOUNT_ID).is(AppContext.getAccountId())), TBL_MONITORING_FOLDERS);
        }
        return remove.getN();
    }

    @Override
    public int deleteMonitor(Long folderId) {
        FolderEntity folderEntity = getMongoTemplate().findOne(new Query(Criteria.where(FOLDER_ID).is(folderId).and(ACCOUNT_ID).is(AppContext.getAccountId())), FolderEntity.class, TBL_MONITORING_TARGETS);
        WriteResult remove;
        if(folderEntity != null && folderEntity.getLocalStatus() == 0){
            Update update = new Update();
            update.set("ls",4);
            remove = getMongoTemplate().updateFirst(Query.query(Criteria.where(FOLDER_ID).is(folderId).and(ACCOUNT_ID).is(AppContext.getAccountId())), update, TBL_MONITORING_TARGETS);
        }else{
            remove = getMongoTemplate().remove(Query.query(Criteria.where(FOLDER_ID).is(folderId).and(ACCOUNT_ID).is(AppContext.getAccountId())), TBL_MONITORING_TARGETS);
        }


        return remove.getN();
    }

    @Override
    public List<FolderMonitorDTO> getMonitor() {
        List<FolderMonitorEntity> entities = getMongoTemplate().find(Query.query(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId())), FolderMonitorEntity.class, TBL_MONITORING_TARGETS);

        List<FolderMonitorDTO> folderMonitorDTOs = ObjectUtils.convert(entities, FolderMonitorDTO.class);

        return folderMonitorDTOs;
    }

    @Override
    public List<FolderMonitorDTO> getMonitorId(Long folderId) {
        List<FolderMonitorEntity> entities = getMongoTemplate().find(Query.query(Criteria.where(FOLDER_ID).is(folderId).and(ACCOUNT_ID).is(AppContext.getAccountId())), FolderMonitorEntity.class, TBL_MONITORING_TARGETS);

        List<FolderMonitorDTO> folderMonitorDTOs = ObjectUtils.convert(entities, FolderMonitorDTO.class);

        return folderMonitorDTOs;
    }

    @Override
    public int deleteMonitorId(Long MonitorId) {
        FolderMonitorEntity folderMonitorEntity = getMongoTemplate().findOne(new Query(Criteria.where(MONITOR_ID).is(MonitorId).and(ACCOUNT_ID).is(AppContext.getAccountId())), FolderMonitorEntity.class, TBL_MONITORING_TARGETS);
        WriteResult remove;
        if(folderMonitorEntity != null && folderMonitorEntity.getLocalstatus() == 0){
            Update update = new Update();
            update.set("ls",4);
            remove = getMongoTemplate().updateFirst(Query.query(Criteria.where(MONITOR_ID).is(MonitorId).and(ACCOUNT_ID).is(AppContext.getAccountId())), update, TBL_MONITORING_TARGETS);
        }else{
            remove = getMongoTemplate().remove(Query.query(Criteria.where(MONITOR_ID).is(MonitorId).and(ACCOUNT_ID).is(AppContext.getAccountId())), TBL_MONITORING_TARGETS);
        }

        return remove.getN();
    }

    @Override
    public void addMonitor(FolderMonitorDTO folderMonitorDTO) {
        FolderMonitorEntity folderMonitorEntity = new FolderMonitorEntity();
        BeanUtils.copyProperties(folderMonitorDTO, folderMonitorEntity);

        getMongoTemplate().insert(folderMonitorEntity, TBL_MONITORING_TARGETS);
    }

    @Override
    public Long getForlderCountByKwid(long kwid) {
        return getMongoTemplate().count(new Query(Criteria.where(MONITOR_ACLID).is(kwid).and(ACCOUNT_ID).is(AppContext.getAccountId())), TBL_MONITORING_TARGETS);
    }

    @Override
    public Class<FolderEntity> getEntityClass() {
        return FolderEntity.class;
    }

    @Override
    public Class<FolderDTO> getDTOClass() {
        return FolderDTO.class;
    }

    @Override
    public List<FolderDTO> find(Map<String, Object> params, int skip, int limit) {
        return null;
    }
}
