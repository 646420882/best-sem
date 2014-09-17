package com.perfect.elasticsearch.service;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.data.elasticsearch.repository.support.AbstractElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchRepositoryFactory;

import javax.annotation.Resource;

/**
 * Created by vbzer_000 on 2014/9/16.
 */
public abstract class AbstractEsDAO extends AbstractElasticsearchRepository {
    @Resource
    private ElasticsearchRepositoryFactory esFactory;

    protected ElasticsearchCrudRepository getRepoInstance() {
        return esFactory.getRepository(ElasticsearchCrudRepository.class);
    }
}
