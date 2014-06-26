package com.perfect.mongodb.dao;

import com.perfect.mongodb.entity.BaiduAccountInfoEntity;
import com.perfect.mongodb.entity.SystemUserEntity;

import java.util.List;

/**
 * Created by vbzer_000 on 2014/6/18.
 */
public interface SystemUserDAO extends BaseDAO<SystemUserEntity> {

    /**
     * 根据用户名查询
     * <br>------------------------------<br>
     *
     * @param userName
     * @return
     */
    SystemUserEntity findByUserName(String userName);

    /**
     * 添加百度账户
     *
     * @param list
     * @param currSystemUserName
     */
    void addBaiduAccount(List<BaiduAccountInfoEntity> list, String currSystemUserName);
}
