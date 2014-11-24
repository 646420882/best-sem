package com.perfect.service;

import com.perfect.entity.CookieEntity;

import java.util.List;

/**
 * Created by baizz on 2014-11-10.
 * 2014-11-24 refactor
 */
public interface CookieService {

    void saveCookie(CookieEntity cookieEntity);

    CookieEntity takeOne();

    void returnOne(CookieEntity cookieEntity);

    List<CookieEntity> allUnused();
}
