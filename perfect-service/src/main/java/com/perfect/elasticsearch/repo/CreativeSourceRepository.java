package com.perfect.elasticsearch.repo;

import com.perfect.entity.CreativeSourceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Created by vbzer_000 on 2014/9/16.
 */
public interface CreativeSourceRepository extends ElasticsearchCrudRepository<CreativeSourceEntity, String> {


    public List<CreativeSourceEntity> findByTitle(String title);


    public List<CreativeSourceEntity> findByTitleOrBody(String title, String body);

    Page<CreativeSourceEntity> findByTitleOrBodyAndHostNot(String title, String desc, String host, Pageable pageable);
}
