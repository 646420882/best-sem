package com.perfect.service.impl;

import com.perfect.entity.backup.AdgroupBakcUpEntity;
import com.perfect.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.mongodb.utils.Pager;
import com.perfect.service.AdgroupBackUpService;

import java.util.Map;

/**
 * Created by XiaoWei on 2014/9/4.
 */
public class AdgroupBackUpServiceImpl extends AbstractUserBaseDAOImpl<AdgroupBakcUpEntity,Long> implements AdgroupBackUpService {
    @Override
    public Class<AdgroupBakcUpEntity> getEntityClass() {
        return null;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }
}
