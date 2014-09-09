package com.perfect.mongodb.dao.impl;

import com.perfect.dao.KeyWordBackUpDAO;
import com.perfect.entity.backup.KeyWordBackUpEntity;
import com.perfect.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.mongodb.utils.Pager;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by XiaoWei on 2014/9/9.
 */
@Component
public class KeyWordBackUpDAOImpl extends AbstractUserBaseDAOImpl<KeyWordBackUpEntity,Long> implements KeyWordBackUpDAO {
    @Override
    public Class<KeyWordBackUpEntity> getEntityClass() {
        return null;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }
}
