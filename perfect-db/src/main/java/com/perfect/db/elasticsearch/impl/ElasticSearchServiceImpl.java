package com.perfect.db.elasticsearch.impl;

import com.perfect.db.elasticsearch.repo.BaseElasticSearchRepository;
import com.perfect.db.elasticsearch.service.EsService;
import com.perfect.dto.creative.EsSearchResultDTO;
import com.perfect.entity.CreativeSourceEntity;
import org.springframework.stereotype.Repository;

/**
 * Created by baizz on 2014-9-30.
 * 2014-11-24 refactor
 */
@Repository("esService")
public class ElasticSearchServiceImpl extends BaseElasticSearchRepository<CreativeSourceEntity, String> implements EsService {

//    public ElasticSearchServiceImpl(String index, String type) {
//        super(index, type);
//    }

    @Override
    public Class<CreativeSourceEntity> getEntityClass() {
        return CreativeSourceEntity.class;
    }

    @Override
    public EsSearchResultDTO search(String query, int page, int size, int[] region) {
        return null;
    }
}
