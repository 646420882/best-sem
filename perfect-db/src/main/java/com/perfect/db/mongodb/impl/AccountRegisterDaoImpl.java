package com.perfect.db.mongodb.impl;

import com.perfect.dao.AccountRegisterDAO;
import com.perfect.entity.SystemUserEntity;
import com.perfect.db.mongodb.base.AbstractSysBaseDAOImpl;
import com.perfect.dao.utils.Pager;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by SubDong on 2014/9/30.
 */
@Repository("accountRegisterDao")
public class AccountRegisterDaoImpl extends AbstractSysBaseDAOImpl<SystemUserEntity, Long> implements AccountRegisterDAO {
    @Override
    public void addAccount(SystemUserEntity systemUserEntity) {
        getSysMongoTemplate().insert(systemUserEntity, "sys_user");
    }

    @Override
    public SystemUserEntity getAccount(String userName) {
        SystemUserEntity user = getSysMongoTemplate().findOne(Query.query(Criteria.where("userName").is(userName)), SystemUserEntity.class, "sys_user");
        return user;
    }


    @Override
    public Class<SystemUserEntity> getEntityClass() {
        return SystemUserEntity.class;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }
}
