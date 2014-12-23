package com.perfect.service;

import com.perfect.dto.CookieDTO;

import java.util.List;

/**
 * Created by baizz on 2014-11-10.
 * 2014-12-23 refactor
 */
public interface CookieService {

    void saveCookie(CookieDTO cookieDTO);

    CookieDTO takeOne();

    void returnOne(String objectId);

    List<CookieDTO> findAll();

    boolean delete(String id);
}
