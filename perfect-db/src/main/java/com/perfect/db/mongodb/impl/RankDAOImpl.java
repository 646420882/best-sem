package com.perfect.db.mongodb.impl;

import com.perfect.dao.RankDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.dto.keyword.RankDTO;
import com.perfect.entity.RankEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by yousheng on 2014/8/19.
 *
 * @author yousheng
 */
@Component
public class RankDAOImpl extends AbstractUserBaseDAOImpl<RankDTO, String> implements RankDAO {


    @Override
    public Class<RankEntity> getEntityClass() {
        return RankEntity.class;
    }

    @Override
    public Class<RankDTO> getDTOClass() {
        return RankDTO.class;
    }

    public List<RankDTO> find(Map<String, Object> params, int skip, int limit) {
        return null;
    }
}
