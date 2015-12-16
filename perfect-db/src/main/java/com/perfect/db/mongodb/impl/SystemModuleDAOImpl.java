package com.perfect.db.mongodb.impl;

import com.mongodb.WriteResult;
import com.perfect.dao.sys.SystemModuleDAO;
import com.perfect.db.mongodb.base.AbstractSysBaseDAOImpl;
import com.perfect.dto.sys.SystemMenuDTO;
import com.perfect.dto.sys.SystemModuleDTO;
import com.perfect.entity.sys.SystemMenuEntity;
import com.perfect.entity.sys.SystemModuleEntity;
import com.perfect.utils.ObjectUtils;
import org.bson.types.ObjectId;
import org.elasticsearch.common.Strings;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

/**
 * Created by yousheng on 15/12/16.
 */
@Repository
public class SystemModuleDAOImpl extends AbstractSysBaseDAOImpl<SystemModuleDTO, String> implements SystemModuleDAO {

    @Override
    public Class<SystemModuleEntity> getEntityClass() {
        return SystemModuleEntity.class;
    }

    @Override
    public Class<SystemModuleDTO> getDTOClass() {
        return SystemModuleDTO.class;
    }

    @Override
    public SystemModuleDTO findByModuleName(String moduleName) {
        SystemModuleEntity systemModuleEntity = getSysMongoTemplate().findOne(Query.query(Criteria.where("moduleName").is(moduleName)),
                getEntityClass());

        return ObjectUtils.convert(systemModuleEntity, getDTOClass());
    }

    @Override
    public SystemModuleDTO findByModuleId(String id) {
        SystemModuleEntity systemModuleEntity = getSysMongoTemplate().findOne(Query.query(Criteria.where(SYSTEM_ID).is
                        (id)),
                getEntityClass());

        if (systemModuleEntity == null) {
            return null;
        }
        SystemModuleDTO systemModuleDTO = ObjectUtils.convert(systemModuleEntity, getDTOClass());

        List<SystemMenuEntity> systemMenuEntities = systemModuleEntity.getMenus();

        systemModuleDTO.setMenus(ObjectUtils.convertToList(systemMenuEntities, SystemMenuDTO.class));
        return systemModuleDTO;
    }


    public SystemModuleDTO updateMenus(SystemModuleDTO systemModuleDTO) {

        SystemModuleEntity systemModuleEntity = ObjectUtils.convert(systemModuleDTO, getEntityClass());

        systemModuleEntity = getSysMongoTemplate().findAndModify(Query.query(Criteria.where("moduleName").is
                (systemModuleEntity
                        .getModuleName())), Update.update("menus", systemModuleEntity.getMenus()), FindAndModifyOptions
                .options().upsert(true).returnNew(true), getEntityClass());

        return ObjectUtils.convert(systemModuleEntity, getDTOClass());

    }

    @Override
    public boolean updateMenus(String moduleName, String menuname) {
        SystemMenuEntity systemMenuEntity = new SystemMenuEntity();
        systemMenuEntity.setMenuName(menuname);

        Update update = new Update();
        update.addToSet("menus", systemMenuEntity);

        WriteResult wr = getSysMongoTemplate().updateFirst(Query.query(Criteria.where("moduleName").is(moduleName)),
                update,
                getEntityClass());
        return wr.getN() == 1;
    }

    @Override
    public boolean deleteMenu(String moduleId, String menuId) {

        SystemModuleDTO systemModuleDTO = findByModuleId(moduleId);

        if (systemModuleDTO == null) {
            return false;
        }

        List<SystemMenuDTO> systemMenuDTOList = systemModuleDTO.getMenus();

        boolean removed = systemMenuDTOList.removeIf((dto) -> dto.getId().equals(menuId));

        if (!removed) {
            return false;
        }
        Update update = new Update();
        update.set("menus", ObjectUtils.convert(systemMenuDTOList, SystemMenuEntity.class));

        WriteResult wr = getSysMongoTemplate().updateFirst(Query.query(Criteria.where(SYSTEM_ID).is(moduleId)),
                update,
                getEntityClass());
        return wr.getN() == 1;
    }

    @Override
    public SystemModuleDTO findByModuleno(int moduleno) {

        SystemModuleEntity systemModuleEntity = getSysMongoTemplate().findOne(Query.query(Criteria.where("moduleno").is
                (moduleno)), getEntityClass());

        return ObjectUtils.convert(systemModuleEntity, getDTOClass());
    }

    @Override
    public boolean insertMenu(String moduleId, SystemMenuDTO systemMenuDTO) {

        systemMenuDTO.setId(new ObjectId(Calendar.getInstance().getTime()).toString());
        Update update = new Update();
        update.addToSet("menus", ObjectUtils.convert(systemMenuDTO, SystemMenuEntity.class));

        WriteResult wr = getSysMongoTemplate().updateFirst(Query.query(Criteria.where(SYSTEM_ID).is(moduleId)),
                update,
                getEntityClass());
        return wr.getN() == 1;
    }

    @Override
    public boolean updateMenus(String moduleId, String menuid, String menuname, Integer order) {
        Update update = new Update();
        if (!Strings.isNullOrEmpty(menuname)) {
            update.set("menus.$.menuName", menuname);
        }

        if (order != null) {
            update.set("menus.$.order", order);
        }

        WriteResult wr = getSysMongoTemplate().updateFirst(Query.query(Criteria.where(SYSTEM_ID).is(moduleId).and
                ("menus._id").is
                (new ObjectId(menuid))), update, getEntityClass());
        return wr.getN() == 1;
    }

    @Override
    public boolean updateModule(SystemModuleDTO systemModuleDTO) {

        if (Strings.isNullOrEmpty(systemModuleDTO.getId())) {
            return false;
        }
        Update update = new Update();
        if (!Strings.isNullOrEmpty(systemModuleDTO.getModuleName())) {
            update.set("moduleName", systemModuleDTO.getModuleName());
        }

        if (!Strings.isNullOrEmpty(systemModuleDTO.getModuleUrl())) {
            update.set("moduleUrl", systemModuleDTO.getModuleUrl());
        }

        WriteResult wr = getSysMongoTemplate().updateFirst(Query.query(Criteria.where(SYSTEM_ID).is(systemModuleDTO
                .getId())), update, getEntityClass());
        return wr.getN() == 1;
    }
}
