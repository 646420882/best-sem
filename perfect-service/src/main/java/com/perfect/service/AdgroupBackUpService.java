package com.perfect.service;

import com.perfect.dao.MongoCrudRepository;
import com.perfect.entity.AdgroupEntity;
import com.perfect.entity.backup.AdgroupBackUpEntity;

/**
 * Created by XiaoWei on 2014/9/4.
 */
public interface AdgroupBackUpService extends MongoCrudRepository<AdgroupBackUpEntity,Long> {
    AdgroupBackUpEntity agReBack(Long id);

}
