package com.perfect.db.mongodb.impl;

import com.perfect.dao.log.UserOperationLogDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.dto.log.UserOperationLogDTO;
import com.perfect.entity.log.UserOperationLogEntity;
import org.springframework.stereotype.Repository;

/**
 * Created by yousheng on 2015/12/13.
 */
@Repository
public class UserOperationLogDAOImpl extends AbstractUserBaseDAOImpl<UserOperationLogDTO, String> implements UserOperationLogDAO {


    public Class<UserOperationLogEntity> getEntityClass() {
        return UserOperationLogEntity.class;
    }

    @Override
    public Class<UserOperationLogDTO> getDTOClass() {
        return UserOperationLogDTO.class;
    }
}
