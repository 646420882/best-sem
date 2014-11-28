package com.perfect.db.mongodb.impl;

import com.perfect.dao.RankDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.dto.keyword.RankDTO;
import com.perfect.paging.Pager;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by yousheng on 2014/8/19.
 *
 * @author yousheng
 */
@Component
public class RankDAOImpl extends AbstractUserBaseDAOImpl<RankDTO, String> implements RankDAO {


    @Override
    public Class<RankDTO> getEntityClass() {
        return RankDTO.class;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }
}
