package com.perfect.db.mongodb.impl;

import com.google.common.collect.Lists;
import com.perfect.dao.sys.LexiconDAO;
import com.perfect.db.mongodb.base.AbstractSysBaseDAOImpl;
import com.perfect.dto.keyword.LexiconDTO;
import com.perfect.entity.keyword.LexiconEntity;
import com.perfect.utils.ObjectUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by baizz on 2014-12-2.
 */
@Component
public class LexiconDAOImpl extends AbstractSysBaseDAOImpl<LexiconDTO, String> implements LexiconDAO {
    @Override
    public void deleteLexiconByTrade(String trade, String category) {
        Query query = new Query();
        if (category != null && category.length() > 0) {
            query.addCriteria(Criteria.where("tr").is(trade).and("cg").is(category));
        } else {
            query.addCriteria(Criteria.where("tr").is(trade));
        }
        getSysMongoTemplate().remove(query, getEntityClass());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<LexiconEntity> getEntityClass() {
        return LexiconEntity.class;
    }

    @Override
    public Class<LexiconDTO> getDTOClass() {
        return LexiconDTO.class;
    }

    @Override
    public Iterable<LexiconDTO> save(Iterable<LexiconDTO> dtos) {
        List<LexiconDTO> dtoList = Lists.newArrayList(dtos);
        List<LexiconEntity> entityList = ObjectUtils.convert(dtoList, getEntityClass());
        getSysMongoTemplate().insertAll(entityList);
        return ObjectUtils.convert(dtoList, getDTOClass());
    }
}
