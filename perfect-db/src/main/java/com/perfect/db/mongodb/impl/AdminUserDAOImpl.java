package com.perfect.db.mongodb.impl;

import com.perfect.dao.admin.AdminUserDAO;
import com.perfect.db.mongodb.base.AbstractSysBaseDAOImpl;
import com.perfect.dto.admin.AdminUserDTO;
import com.perfect.entity.admin.AdminUserEntity;
import com.perfect.utils.ObjectUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by yousheng on 15/12/15.
 */
@Repository
public class AdminUserDAOImpl extends AbstractSysBaseDAOImpl<AdminUserDTO, String> implements AdminUserDAO {
    @Override
    public AdminUserDTO findByUserName(String username) {
        AdminUserEntity adminUserEntity = getSysMongoTemplate().findOne(Query.query(Criteria.where("name").is
                        (username)),
                getEntityClass());

        if (adminUserEntity == null) {
            return null;
        }

        return ObjectUtils.convert(adminUserEntity, getDTOClass());
    }

    @Override
    public Class<AdminUserEntity> getEntityClass() {
        return AdminUserEntity.class;
    }

    @Override
    public Class<AdminUserDTO> getDTOClass() {
        return AdminUserDTO.class;
    }
}
