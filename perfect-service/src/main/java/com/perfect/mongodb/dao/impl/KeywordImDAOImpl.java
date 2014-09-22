package com.perfect.mongodb.dao.impl;

import com.perfect.dao.KeywordImDAO;
import com.perfect.entity.KeywordImEntity;
import com.perfect.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.mongodb.utils.Pager;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by XiaoWei on 2014/9/22.
 */
@Repository("keywordImDAO")
public class KeywordImDAOImpl extends AbstractUserBaseDAOImpl<KeywordImEntity,Long> implements KeywordImDAO {
    @Override
    public Class<KeywordImEntity> getEntityClass() {
        return null;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }
}
