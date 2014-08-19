package com.perfect.mongodb.dao.impl;

import com.perfect.api.baidu.BaiduService;
import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.dao.AccountDAO;
import com.perfect.dao.SystemUserDAO;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.entity.SystemUserEntity;
import com.perfect.mongodb.base.AbstractSysBaseDAOImpl;
import com.perfect.mongodb.utils.Pager;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by vbzer_000 on 2014-6-19.
 */
@Repository("systemUserDAO")
public class SystemUserDAOImpl extends AbstractSysBaseDAOImpl<SystemUserEntity, String> implements SystemUserDAO {


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
    public SystemUserEntity findByAid(long aid) {
        Query query = Query.query(Criteria.where("bdAccounts._id").is(aid));
        return getSysMongoTemplate().findOne(query, SystemUserEntity.class);
    }

    @Override
    public SystemUserEntity findByUserName(String userName) {
        SystemUserEntity user = getSysMongoTemplate().
                find(Query.query(Criteria.where("userName").is(userName)), SystemUserEntity.class, "sys_user").get(0);
        return user;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }

    @Override
    public Class<SystemUserEntity> getEntityClass() {
        return SystemUserEntity.class;
    }
}
