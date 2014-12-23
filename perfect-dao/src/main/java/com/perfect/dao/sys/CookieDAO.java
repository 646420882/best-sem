package com.perfect.dao.sys;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.CookieDTO;

/**
 * Created by baizz on 2014-11-10.
 * 2014-12-23 refactor
 */
public interface CookieDAO extends HeyCrudRepository<CookieDTO, String> {

    CookieDTO takeOne();

    void returnOne(String objectId);

}
