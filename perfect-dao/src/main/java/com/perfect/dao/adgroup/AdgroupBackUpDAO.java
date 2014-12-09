package com.perfect.dao.adgroup;


import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.backup.AdgroupBackupDTO;

/**
 * Created by XiaoWei on 2014/9/4.
 */
public interface AdgroupBackUpDAO extends HeyCrudRepository<AdgroupBackupDTO,Long> {
    public AdgroupBackupDTO findOne(String oid);
    public AdgroupBackupDTO findByLongId(Long oid);
    void deleteByLongId(Long id);
}
