package com.perfect.dao;

import com.perfect.entity.CookieEntity;

import java.util.List;

/**
 * Created by baizz on 2014-11-10.
 */
public interface CookieDAO extends MongoCrudRepository<CookieEntity, String> {

    CookieEntity takeOne();

    void returnOne(CookieEntity cookieEntity);

    List<CookieEntity> allUnused();

}
