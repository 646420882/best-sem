package com.perfect.app.bdlogin.service;

import com.perfect.app.bdlogin.core.BaiduHttpLogin;
import com.perfect.dao.CookieDAO;
import com.perfect.entity.CookieEntity;
import com.perfect.service.CookieService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

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

    @Override
    public boolean simulateLogin(String username, String password, String imageCode, String cookies) {
        try {
            boolean isSuccess = BaiduHttpLogin.execute(username, password, imageCode, cookies);
            return isSuccess;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
