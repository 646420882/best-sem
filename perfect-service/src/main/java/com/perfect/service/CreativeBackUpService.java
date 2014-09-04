package com.perfect.service;

import com.perfect.dao.MongoCrudRepository;
import com.perfect.entity.backup.CreativeBackUpEntity;

/**
 * Created by XiaoWei on 2014/9/4.
 */
public interface CreativeBackUpService extends MongoCrudRepository<CreativeBackUpEntity,Long>{
    CreativeBackUpEntity findByStringId(String id);
    CreativeBackUpEntity findByLongId(Long crid);
    void deleteByLongId(Long crid);
    CreativeBackUpEntity reBack(Long crid);
}
