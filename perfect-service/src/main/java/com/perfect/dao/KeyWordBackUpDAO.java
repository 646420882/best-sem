package com.perfect.dao;

import com.perfect.entity.backup.KeyWordBackUpEntity;

/**
 * Created by XiaoWei on 2014/9/9.
 */
public interface KeyWordBackUpDAO extends MongoCrudRepository<KeyWordBackUpEntity,Long>  {


    KeyWordBackUpEntity findByObjectId(String id);

    void deleteByObjectId(String id);

    KeyWordBackUpEntity findById(long id);

    void deleteByKwid(long kwid);

    boolean existsByObjectId(String id);
}
