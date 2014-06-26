package com.perfect.dao;


import com.perfect.entity.AccountInfoEntity;

/**
 * Created by vbzer_000 on 2014/6/18.
 */
public interface AccountDAO extends BaseDAO<AccountInfoEntity> {

    boolean isExists(String id);
}
