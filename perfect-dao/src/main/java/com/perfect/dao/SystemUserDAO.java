package com.perfect.dao;

import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.entity.SystemUserEntity;

import java.util.List;

/**
 * Created by vbzer_000 on 2014/6/18.
 */
public interface SystemUserDAO extends MongoCrudRepository<SystemUserEntity, String> {

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

    void updateAccount(String userName);

    SystemUserEntity findByAid(long aid);

    void insertAccountInfo(String user, BaiduAccountInfoEntity baiduAccountInfoEntity);

    void removeAccountInfo(Long id);
}
