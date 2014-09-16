package com.perfect.service;

import com.perfect.dao.MongoCrudRepository;
import com.perfect.entity.KeywordEntity;
import com.perfect.entity.backup.KeyWordBackUpEntity;

/**
 * Created by XiaoWei on 2014/9/9.
 */
public interface KeyWordBackUpService extends MongoCrudRepository<KeyWordBackUpEntity,Long> {

    KeywordEntity reducUpdate(String id);

    void reducDel(String id);
}
