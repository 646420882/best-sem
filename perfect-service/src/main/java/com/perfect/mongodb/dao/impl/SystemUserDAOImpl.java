package com.perfect.mongodb.dao.impl;

import com.perfect.api.baidu.BaiduService;
import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.dao.AccountDAO;
import com.perfect.dao.SystemUserDAO;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.entity.SystemUserEntity;
import com.perfect.mongodb.utils.BaseMongoTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vbzer_000 on 2014-6-19.
 */
@Repository("systemUserDAO")
public class SystemUserDAOImpl extends AbstractBaseDAOImpl<SystemUserEntity> implements SystemUserDAO {
    private MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate("sys");

    @Resource
    private AccountDAO accountDAO;

    @Resource
    private BaiduService baiduService;

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
        getMongoTemplate().updateFirst(Query.query(Criteria.where("userName").is(currSystemUserName)), Update.update("baiduAccountInfos", list1), "SystemUser");
    }

    @Override
    public void updateAccount(String userName) {
        SystemUserEntity systemUserEntity = findByUserName(userName);
        List<BaiduAccountInfoEntity> list = systemUserEntity.getBaiduAccountInfoEntities();
        try {
            for (BaiduAccountInfoEntity entity : list) {
                ServiceFactory sf = ServiceFactory.getInstance(entity.getBaiduUserName(), entity.getBaiduPassword(), entity.getToken(), null);
                baiduService.init(sf);
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
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
                find(Query.query(Criteria.where("userName").is(userName)), SystemUserEntity.class, "sys_user").get(0);
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
        return mongoTemplate.findAll(SystemUserEntity.class, "sys_user");
    }

    @Override
    public void deleteAll() {
        getMongoTemplate().dropCollection(SystemUserEntity.class);
    }
}
