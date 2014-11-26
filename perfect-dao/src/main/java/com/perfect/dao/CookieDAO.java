package com.perfect.dao;


import com.perfect.dto.CookieDTO;

import java.util.List;

/**
 * Created by baizz on 2014-11-10.
 */
public interface CookieDAO {

    CookieDTO takeOne();

    void returnOne(CookieDTO cookieDTO);

    List<CookieDTO> allUnused();

}
