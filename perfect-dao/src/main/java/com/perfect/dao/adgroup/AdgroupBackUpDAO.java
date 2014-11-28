package com.perfect.dao.adgroup;


import com.perfect.dto.backup.AdgroupBackupDTO;

/**
 * Created by XiaoWei on 2014/9/4.
 */
public interface AdgroupBackUpDAO  {
    public AdgroupBackupDTO findOne(String oid);
    public AdgroupBackupDTO findByLongId(Long oid);
    void deleteByLongId(Long id);
}
