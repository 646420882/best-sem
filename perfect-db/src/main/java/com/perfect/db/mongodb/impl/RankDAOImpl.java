package com.perfect.db.mongodb.impl;

import com.perfect.dao.RankDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.dto.keyword.RankDTO;
import org.springframework.stereotype.Component;

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
    public Class<RankDTO> getDTOClass() {
        return RankDTO.class;
    }
}
