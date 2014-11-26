package com.perfect.service;

import com.perfect.dao.MongoCrudRepository;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.entity.KeywordEntity;
import com.perfect.entity.backup.KeyWordBackUpEntity;

/**
 * Created by XiaoWei on 2014/9/9.
 */
public interface KeyWordBackUpService {

    KeywordDTO reducUpdate(String id);

    void reducDel(String id);
}
