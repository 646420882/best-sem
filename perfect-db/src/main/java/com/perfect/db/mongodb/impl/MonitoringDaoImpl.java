package com.perfect.db.mongodb.impl;

import com.mongodb.WriteResult;
import com.perfect.core.AppContext;
import com.perfect.dao.MonitoringDao;
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

import static com.perfect.commons.constants.MongoEntityConstants.*;

/**
 * Created by SubDong on 2014/9/15.
 */
@Repository("monitoringDao")
public class MonitoringDaoImpl extends AbstractSysBaseDAOImpl<FolderDTO,Long> implements MonitoringDao {
    @Override
    public List<FolderDTO> getForlder() {
        List<FolderEntity> forlderEntities = getMongoTemplate().find(new Query(), FolderEntity.class, TBL_MONITORING_FOLDERS + "_" + AppContext.getAccountId());

        List<FolderDTO> folderDTOs = ObjectUtils.convert(forlderEntities,FolderDTO.class);

        return folderDTOs;
    }

    @Override
    public List<FolderDTO> getForlderId(Long folderId) {
        List<FolderEntity> forlderEntities = getMongoTemplate().find(Query.query(Criteria.where(FOLDER_ID).is(folderId)), FolderEntity.class, TBL_MONITORING_FOLDERS + "_" + AppContext.getAccountId());

        List<FolderDTO> folderDTOs = ObjectUtils.convert(forlderEntities,FolderDTO.class);

        return folderDTOs;
    }

    @Override
    public boolean updataForlderId(Long folderId, String folderName) {
        WriteResult writeResult = getMongoTemplate().updateFirst(Query.query(Criteria.where(FOLDER_ID).is(folderId)), Update.update("fdna",folderName), TBL_MONITORING_FOLDERS + "_" + AppContext.getAccountId());
        boolean b = writeResult.isUpdateOfExisting();
        return b;
    }

    @Override
    public void addFolder(FolderDTO folderDTO) {
        FolderEntity folderEntity = new FolderEntity();
        BeanUtils.copyProperties(folderDTO,folderEntity);

        getMongoTemplate().insert(folderEntity, TBL_MONITORING_FOLDERS + "_" + AppContext.getAccountId());
    }

    @Override
    public WriteResult deleteFoder(Long folderId) {
        WriteResult remove = getMongoTemplate().remove(Query.query(Criteria.where(FOLDER_ID).is(folderId)), TBL_MONITORING_FOLDERS + "_" + AppContext.getAccountId());
        return remove;
    }

    @Override
    public WriteResult deleteMonitor(Long folderId) {
        WriteResult remove = getMongoTemplate().remove(Query.query(Criteria.where(FOLDER_ID).is(folderId)), TBL_MONITORING_TARGETS + "_" + AppContext.getAccountId());
        return remove;
    }

    @Override
    public List<FolderMonitorDTO> getMonitor() {
        List<FolderMonitorEntity> entities = getMongoTemplate().find(new Query(), FolderMonitorEntity.class, TBL_MONITORING_TARGETS + "_" + AppContext.getAccountId());

        List<FolderMonitorDTO> folderMonitorDTOs = ObjectUtils.convert(entities,FolderMonitorDTO.class);

        return folderMonitorDTOs;
    }

    @Override
    public List<FolderMonitorDTO> getMonitorId(Long folderId) {
        List<FolderMonitorEntity> entities = getMongoTemplate().find(Query.query(Criteria.where(FOLDER_ID).is(folderId)), FolderMonitorEntity.class, TBL_MONITORING_TARGETS + "_" + AppContext.getAccountId());

        List<FolderMonitorDTO> folderMonitorDTOs = ObjectUtils.convert(entities,FolderMonitorDTO.class);

        return folderMonitorDTOs;
    }

    @Override
    public WriteResult deleteMonitorId(Long MonitorId) {
        WriteResult remove = getMongoTemplate().remove(Query.query(Criteria.where(MONITOR_ID).is(MonitorId)), TBL_MONITORING_TARGETS + "_" + AppContext.getAccountId());
        return remove;
    }

    @Override
    public void addMonitor(FolderMonitorDTO folderMonitorDTO) {
        FolderMonitorEntity folderMonitorEntity = new FolderMonitorEntity();
        BeanUtils.copyProperties(folderMonitorDTO,folderMonitorEntity);

        getMongoTemplate().insert(folderMonitorEntity, TBL_MONITORING_TARGETS + "_" + AppContext.getAccountId());
    }

    @Override
    public Long getForlderCountByKwid(long kwid) {
        return getMongoTemplate().count(new Query(Criteria.where(MONITOR_ACLID).is(kwid)), TBL_MONITORING_TARGETS + "_" + AppContext.getAccountId());
    }

    @Override
    public Class<FolderDTO> getEntityClass() {
        return FolderDTO.class;
    }
}
