package com.perfect.mongodb.dao.impl;

import com.perfect.dao.CensusDAO;
import com.perfect.entity.CensusEntity;
import com.perfect.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.mongodb.base.BaseMongoTemplate;
import com.perfect.mongodb.utils.EntityConstants;
import com.perfect.mongodb.utils.Pager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by XiaoWei on 2014/11/11.
 */
@Component
public class CensusDAOImpl extends AbstractUserBaseDAOImpl<CensusEntity,Long> implements CensusDAO {
    @Override
    public Class<CensusEntity> getEntityClass() {
        return null;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }

    @Override
    public CensusEntity saveParams(CensusEntity censusEntity) {
        MongoTemplate mongoTemplate= BaseMongoTemplate.getSysMongo();

        if(mongoTemplate.exists(new Query(Criteria.where("uuid").is(censusEntity.getUuid())),EntityConstants.SYS_CENSUS)){
            mongoTemplate.save(censusEntity, EntityConstants.SYS_CENSUS);
        }else{
            System.out.println("存在相同uuid，请在这里操作!");
        }
        return censusEntity;
    }
}
