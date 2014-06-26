package com.perfect.mongodb.dao.impl;

import com.perfect.mongodb.dao.SystemUserDAO;
import com.perfect.mongodb.entity.BaiduAccountInfoEntity;
import com.perfect.mongodb.entity.SystemUserEntity;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vbzer_000 on 2014-6-19.
 */
@Repository("systemUserDAO")
public class SystemUserDAOImpl extends AbstractBaseDAO<SystemUserEntity> implements SystemUserDAO {

    @Override
    public void addBaiduAccount(List<BaiduAccountInfoEntity> list, String currSystemUserName) {
        SystemUserEntity currSystemUserEntity = findByUserName(currSystemUserName);
        List<BaiduAccountInfoEntity> list1 = currSystemUserEntity.getBaiduAccountInfoEntities();
        if (list1 == null) {
            list1 = new ArrayList<>();
            for (BaiduAccountInfoEntity entity : list)
                list1.add(entity);
        } else {
            for (BaiduAccountInfoEntity entity : list)
                list1.add(entity);
        }
        getMongoTemplate().updateFirst(new Query(Criteria.where("userName").is(currSystemUserName)), Update.update("baiduAccountInfos", list1), "SystemUser");
    }

    @Override
    public void updateById(SystemUserEntity systemUserEntity) {

    }

    @Override
    public void update(SystemUserEntity s, SystemUserEntity d) {

    }

    @Override
    public SystemUserEntity findById(String id) {
        return null;
    }

    @Override
    public SystemUserEntity findByUserName(String userName) {
        SystemUserEntity user = getMongoTemplate().
                find(new Query(Criteria.where("userName").is(userName)), SystemUserEntity.class, "SystemUser")
                .get(0);
        return user;
    }


    @Override
    public List<SystemUserEntity> find(SystemUserEntity systemUserEntity, int skip, int limit) {
        return null;
    }

    @Override
    public SystemUserEntity findAndModify(SystemUserEntity q, SystemUserEntity u) {
        return null;
    }

    @Override
    public SystemUserEntity findAndRemove(SystemUserEntity systemUserEntity) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public List<SystemUserEntity> findAll() {
        return getMongoTemplate().findAll(SystemUserEntity.class);
    }

    @Override
    public void deleteAll() {
        getMongoTemplate().dropCollection(SystemUserEntity.class);
    }
}
