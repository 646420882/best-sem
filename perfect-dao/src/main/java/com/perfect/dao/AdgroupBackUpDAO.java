package com.perfect.dao;

import com.perfect.entity.backup.AdgroupBackUpEntity;

/**
 * Created by XiaoWei on 2014/9/4.
 */
public interface AdgroupBackUpDAO extends MongoCrudRepository<AdgroupBackUpEntity, Long> {
    public AdgroupBackUpEntity findOne(String oid);
    public AdgroupBackUpEntity findByLongId(Long oid);
    void deleteByLongId(Long id);
}
