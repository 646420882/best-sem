package com.perfect.service;

import com.perfect.dto.CookieDTO;

import java.util.List;

/**
 * Created by baizz on 2014-11-10.
 * 2014-11-26 refactor
 */
public interface CookieService {

    void saveCookie(CookieDTO cookieDTO);

    CookieDTO takeOne();

    void returnOne(CookieDTO cookieDTO);

    List<CookieDTO> allUnused();
}
