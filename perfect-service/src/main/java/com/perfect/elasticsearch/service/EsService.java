package com.perfect.elasticsearch.service;

import com.perfect.entity.CreativeSourceEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.data.elasticsearch.repository.support.AbstractElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchRepositoryFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by vbzer_000 on 2014/9/16.
 */
public interface EsService {
    com.perfect.dto.EsSearchResultDTO search(String query, int page, int size, int[] region);
}
