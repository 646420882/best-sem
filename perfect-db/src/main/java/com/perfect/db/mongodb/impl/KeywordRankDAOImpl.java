package com.perfect.db.mongodb.impl;

import com.perfect.dao.keyword.KeywordRankDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.dto.keyword.KeywordRankDTO;
import com.perfect.entity.bidding.KeywordRankEntity;
import com.perfect.utils.ObjectUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

/**
 * Created by vbzer_000 on 2014/8/31.
 */
@Component
public class KeywordRankDAOImpl extends AbstractUserBaseDAOImpl<KeywordRankDTO, String> implements KeywordRankDAO {
    @Override
    public Class<KeywordRankEntity> getEntityClass() {
        return KeywordRankEntity.class;
    }

    @Override
    public KeywordRankDTO findByKeywordId(String id) {
        KeywordRankEntity keywordRankEntity = getMongoTemplate().findOne(Query.query(Criteria.where(KEYWORD_ID).is(id)), getEntityClass());

        return ObjectUtils.convertToObject(keywordRankEntity, getDTOClass());
    }

    @Override
    public Class<KeywordRankDTO> getDTOClass() {
        return KeywordRankDTO.class;
    }
}
