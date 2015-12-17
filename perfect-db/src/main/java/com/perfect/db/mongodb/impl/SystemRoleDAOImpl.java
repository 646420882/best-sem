package com.perfect.db.mongodb.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mongodb.WriteResult;
import com.perfect.dao.sys.SystemRoleDAO;
import com.perfect.db.mongodb.base.AbstractSysBaseDAOImpl;
import com.perfect.dto.sys.SystemRoleDTO;
import com.perfect.entity.sys.SystemRoleEntity;
import com.perfect.utils.ObjectUtils;
import com.perfect.utils.json.JSONUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by yousheng on 15/12/17.
 */
@Repository
public class SystemRoleDAOImpl extends AbstractSysBaseDAOImpl<SystemRoleDTO, String> implements SystemRoleDAO {
    @Override
    public Class<SystemRoleEntity> getEntityClass() {
        return SystemRoleEntity.class;
    }

    @Override
    public Class<SystemRoleDTO> getDTOClass() {
        return SystemRoleDTO.class;
    }

    public List<SystemRoleDTO> find(String name, Boolean superAdmin, int page, int size, String sort, boolean asc) {

        Query query = new Query();


        if (!Strings.isNullOrEmpty(name)) {
            query.addCriteria(Criteria.where("name").regex("*" + name + "*"));
        }

        if (superAdmin != null) {
            query.addCriteria(Criteria.where("superAdmin").is(superAdmin));
        }

        query.with(new PageRequest(page - 1, size, (asc) ? Sort.Direction.ASC : Sort.Direction.DESC, sort));


        List<SystemRoleEntity> systemRoleEntities = getSysMongoTemplate().find(query, getEntityClass());

        return ObjectUtils.convert(systemRoleEntities, getDTOClass());
    }

    @Override
    public boolean update(String roleid, SystemRoleDTO systemRoleDTO) {
        boolean exists = getSysMongoTemplate().exists(Query.query(Criteria.where(SYSTEM_ID).is(roleid)), getEntityClass());

        if (!exists) {
            return false;
        }

        Update update = new Update();

        JsonNode jsonNode = JSONUtils.getJsonObject(systemRoleDTO);

        Lists.newArrayList(jsonNode.fields()).forEach((tmp) -> {
            if (tmp.getValue().isNumber()) {
                update.set(tmp.getKey(), tmp.getValue().numberValue());
            } else if (tmp.getValue().isBoolean()) {
                update.set(tmp.getKey(), tmp.getValue().booleanValue());
            } else {
                update.set(tmp.getKey(), tmp.getValue().asText());
            }
        });
        WriteResult wr = getSysMongoTemplate().updateFirst(Query.query(Criteria.where(SYSTEM_ID).is(roleid)), update, getEntityClass());

        return wr.getN() == 1;
    }

}
