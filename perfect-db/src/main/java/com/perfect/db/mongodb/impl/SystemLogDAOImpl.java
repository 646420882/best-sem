package com.perfect.db.mongodb.impl;

import com.perfect.dao.log.SystemLogDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.dto.log.SystemLogDTO;
import com.perfect.entity.log.SystemLogEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yousheng on 2015/12/13.
 */
@Repository
public class SystemLogDAOImpl extends AbstractUserBaseDAOImpl<SystemLogDTO, String> implements SystemLogDAO {


    public Class<SystemLogEntity> getEntityClass() {
        return SystemLogEntity.class;
    }

    @Override
    public Class<SystemLogDTO> getDTOClass() {
        return SystemLogDTO.class;
    }
}
