package com.perfect.service;

import com.perfect.entity.creative.CreativeSourceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by vbzer_000 on 2014/9/16.
 */
public interface CreativeSourceService {

    void save(CreativeSourceEntity creativeSourceEntity);

    Page<CreativeSourceEntity> search(String title, String desc, String url, Pageable pageable);

    void save(List<CreativeSourceEntity> resultList);

    boolean exits(String id);
}
