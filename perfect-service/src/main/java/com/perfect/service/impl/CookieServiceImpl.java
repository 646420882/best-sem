package com.perfect.service.impl;

import com.perfect.dao.CookieDAO;
import com.perfect.entity.CookieEntity;
import com.perfect.service.CookieService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by baizz on 2014-11-10.
 */
@Service("cookieService")
public class CookieServiceImpl implements CookieService {

    @Resource
    private CookieDAO cookieDAO;

    @Override
    public void saveCookie(CookieEntity cookieEntity) {
        cookieDAO.save(cookieEntity);
    }
}
