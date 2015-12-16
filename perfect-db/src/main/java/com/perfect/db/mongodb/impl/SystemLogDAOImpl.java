package com.perfect.db.mongodb.impl;

import com.google.common.base.Strings;
import com.perfect.core.AppContext;
import com.perfect.core.SystemUserInfo;
import com.perfect.dao.sys.SystemLogDAO;
import com.perfect.db.mongodb.base.AbstractSysBaseDAOImpl;
import com.perfect.dto.sys.SystemLogDTO;
import com.perfect.entity.sys.SystemLogEntity;
import com.perfect.param.SystemLogParams;
import com.perfect.utils.ObjectUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yousheng on 15/12/16.
 */
@Repository
public class SystemLogDAOImpl extends AbstractSysBaseDAOImpl<SystemLogDTO, String> implements SystemLogDAO {

    @Override
    public Class<SystemLogEntity> getEntityClass() {
        return SystemLogEntity.class;
    }

    @Override
    public Class<SystemLogDTO> getDTOClass() {
        return SystemLogDTO.class;
    }

    @Override
    public List<SystemLogDTO> list(SystemLogParams params, int page, int size, String sort, boolean asc) {

        Query query = new Query();
        if (params != null) {
            if (params.getStart() != null) {
                query = query.addCriteria(Criteria.where("time").gte(params.getStart()));
            }

            if (params.getEnd() != null) {
                query = query.addCriteria(Criteria.where("time").lte(params.getEnd()));
            }

            if (!Strings.isNullOrEmpty(params.getUser())) {
                query = query.addCriteria(Criteria.where("user").is(params.getUser()));
            }
        }
        PageRequest pageRequest = new PageRequest(page, size, (asc) ? Sort.Direction.ASC : Sort.Direction.DESC, sort);
        List<SystemLogEntity> systemLogEntities = getSysMongoTemplate().find(query.with(pageRequest), getEntityClass());

        return ObjectUtils.convert(systemLogEntities, getDTOClass());
    }

    @Override
    public void log(String txt) {
        SystemLogEntity systemLogEntity = new SystemLogEntity();

        SystemUserInfo systemUserInfo = AppContext.getSystemUserInfo();
        if (systemUserInfo == null) {
            return;
        }
        systemLogEntity.setIp(systemUserInfo.getIp());
        systemLogEntity.setUser(systemUserInfo.getUser());
        systemLogEntity.setTime(System.currentTimeMillis());
        systemLogEntity.setDesc(txt);

        getSysMongoTemplate().insert(systemLogEntity);
        return;
    }
}
