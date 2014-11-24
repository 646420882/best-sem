package com.perfect.dao.mongodb.impl;

import com.perfect.dao.RankDAO;
import com.perfect.entity.RankEntity;
import com.perfect.dao.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.dao.mongodb.utils.Pager;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by yousheng on 2014/8/19.
 *
 * @author yousheng
 */
@Component
public class RankDAOImpl extends AbstractUserBaseDAOImpl<RankEntity, String> implements RankDAO {


    @Override
    public Class<RankEntity> getEntityClass() {
        return RankEntity.class;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }
}
