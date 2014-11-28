package com.perfect.service.impl;

import com.perfect.dao.sys.CookieDAO;
import com.perfect.dto.CookieDTO;
import com.perfect.service.CookieService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by baizz on 2014-11-10.
 * 2014-11-26 refactor
 */
@Service("cookieService")
public class CookieServiceImpl implements CookieService {

    @Resource
    private CookieDAO cookieDAO;

    @Override
    public void saveCookie(CookieDTO cookieDTO) {
        cookieDAO.save(cookieDTO);
    }

    @Override
    public CookieDTO takeOne() {
        return cookieDAO.takeOne();
    }

    @Override
    public void returnOne(CookieDTO cookieDTO) {
        cookieDAO.returnOne(cookieDTO);
    }

    @Override
    public List<CookieDTO> allUnused() {
        return cookieDAO.allUnused();
    }

}
