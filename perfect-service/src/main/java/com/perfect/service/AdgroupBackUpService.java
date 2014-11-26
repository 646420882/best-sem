package com.perfect.service;

import com.perfect.dao.MongoCrudRepository;
import com.perfect.dto.backup.AdgroupBackupDTO;
import com.perfect.entity.backup.AdgroupBackUpEntity;

/**
 * Created by XiaoWei on 2014/9/4.
 */
public interface AdgroupBackUpService  {
    AdgroupBackupDTO agReBack(Long id);

}
