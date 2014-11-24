package com.perfect.dao.elasticsearch.service.impl;

import com.perfect.dao.elasticsearch.repo.BaseElasticSearchRepository;
import com.perfect.entity.CreativeSourceEntity;
import org.springframework.stereotype.Component;

/**
 * Created by baizz on 2014-9-30.
 */
@Component
public class ElasticSearchServiceImpl extends BaseElasticSearchRepository<CreativeSourceEntity, String> {

//    public ElasticSearchServiceImpl(String index, String type) {
//        super(index, type);
//    }

    @Override
    public Class<CreativeSourceEntity> getEntityClass() {
        return CreativeSourceEntity.class;
    }
}
