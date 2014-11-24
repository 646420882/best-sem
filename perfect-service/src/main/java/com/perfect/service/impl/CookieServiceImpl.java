package com.perfect.service.impl;

import com.perfect.dao.CookieDAO;
import com.perfect.entity.CookieEntity;
import com.perfect.service.CookieService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by baizz on 2014-11-10.
 * 2014-11-24 refactor
 */
@Service("cookieService")
public class CookieServiceImpl implements CookieService {

    @Resource
    private CookieDAO cookieDAO;

    @Override
    public void saveCookie(CookieEntity cookieEntity) {
        cookieDAO.save(cookieEntity);
    }

    @Override
    public CookieEntity takeOne() {
        return cookieDAO.takeOne();
    }

    @Override
    public void returnOne(CookieEntity cookieEntity) {
        cookieDAO.returnOne(cookieEntity);
    }

    @Override
    public List<CookieEntity> allUnused() {
        return cookieDAO.allUnused();
    }

}
