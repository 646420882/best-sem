package com.perfect.dao;


import com.perfect.dto.backup.AdgroupBackupDTO;

/**
 * Created by XiaoWei on 2014/9/4.
 */
public interface AdgroupBackUpDAO extends MongoCrudRepository<AdgroupBackupDTO, Long> {
    public AdgroupBackupDTO findOne(String oid);
    public AdgroupBackupDTO findByLongId(Long oid);
    void deleteByLongId(Long id);
}
