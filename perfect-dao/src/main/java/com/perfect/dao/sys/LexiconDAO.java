package com.perfect.dao.sys;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.keyword.LexiconDTO;

/**
 * Created by baizz on 2014-12-2.
 */
public interface LexiconDAO extends HeyCrudRepository<LexiconDTO, String> {

    void deleteLexiconByTrade(String trade, String category);
}
