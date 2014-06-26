package com.perfect.mongodb.dao.impl;

import com.perfect.mongodb.dao.SystemUserDAO;
import com.perfect.mongodb.entity.BaiduAccountInfo;
import com.perfect.mongodb.entity.SystemUser;
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
public class SystemUserDAOImpl extends AbstractBaseDAO<SystemUser> implements SystemUserDAO {

    @Override
    public void addBaiduAccount(List<BaiduAccountInfo> list, String currSystemUserName) {
        SystemUser currSystemUser = findByUserName(currSystemUserName);
        List<BaiduAccountInfo> list1 = currSystemUser.getBaiduAccountInfos();
        if (list1 == null) {
            list1 = new ArrayList<>();
            for (BaiduAccountInfo entity : list)
                list1.add(entity);
        } else {
            for (BaiduAccountInfo entity : list)
                list1.add(entity);
        }
        getMongoTemplate().updateFirst(new Query(Criteria.where("userName").is(currSystemUserName)), Update.update("baiduAccountInfos", list1), "SystemUser");
    }

    @Override
    public void updateById(SystemUser systemUser) {

    }

    @Override
    public void update(SystemUser s, SystemUser d) {

    }

    @Override
    public SystemUser findById(String id) {
        return null;
    }

    @Override
    public SystemUser findByUserName(String userName) {
        SystemUser user = getMongoTemplate().
                find(new Query(Criteria.where("userName").is(userName)), SystemUser.class, "SystemUser")
                .get(0);
        return user;
    }


    @Override
    public List<SystemUser> find(SystemUser systemUser, int skip, int limit) {
        return null;
    }

    @Override
    public SystemUser findAndModify(SystemUser q, SystemUser u) {
        return null;
    }

    @Override
    public SystemUser findAndRemove(SystemUser systemUser) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public List<SystemUser> findAll() {
        return getMongoTemplate().findAll(SystemUser.class);
    }

    @Override
    public void deleteAll() {
        getMongoTemplate().dropCollection(SystemUser.class);
    }
}
