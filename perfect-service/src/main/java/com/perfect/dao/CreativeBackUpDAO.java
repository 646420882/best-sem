package com.perfect.dao;

import com.perfect.entity.backup.CreativeBackUpEntity;

/**
 * Created by XiaoWei on 2014/9/4.
 */

public interface CreativeBackUpDAO extends  MongoCrudRepository<CreativeBackUpEntity,Long> {
    CreativeBackUpEntity findByStringId(String id);
    CreativeBackUpEntity findByLongId(Long crid);
    void deleteByLongId(Long crid);
}
