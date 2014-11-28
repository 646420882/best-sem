package com.perfect.dao.sys;


import com.perfect.dao.MongoCrudRepository;
import com.perfect.dto.CookieDTO;

import java.util.List;

/**
 * Created by baizz on 2014-11-10.
 */
public interface CookieDAO extends MongoCrudRepository<CookieDTO, String> {

    CookieDTO takeOne();

    void returnOne(CookieDTO cookieDTO);

    List<CookieDTO> allUnused();

}
