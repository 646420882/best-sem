package com.perfect.service.impl;

import com.perfect.entity.backup.KeyWordBackUpEntity;
import com.perfect.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.mongodb.utils.Pager;
import com.perfect.service.KeyWordBackUpService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by XiaoWei on 2014/9/9.
 */
@Service
public class KeyWordBackUpServiceImpl extends AbstractUserBaseDAOImpl<KeyWordBackUpEntity,Long> implements KeyWordBackUpService {
    @Override
    public Class<KeyWordBackUpEntity> getEntityClass() {
        return null;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }
}
