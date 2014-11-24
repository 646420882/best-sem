package com.perfect.service.impl;

import com.perfect.db.elasticsearch.repo.CreativeSourceRepository;
import com.perfect.entity.CreativeSourceEntity;
import com.perfect.service.CreativeSourceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by vbzer_000 on 2014/9/16.
 */
@Component("creativeSourceService")
public class CreativeSourceServiceImpl implements CreativeSourceService {

    @Resource
    private CreativeSourceRepository creativeSourceRepository;

    @Override
    public void save(CreativeSourceEntity creativeSourceEntity) {
        creativeSourceRepository.save(creativeSourceEntity);
    }

    @Override
    public Page<CreativeSourceEntity> search(String title, String desc, String url, Pageable pageable) {
        Page<CreativeSourceEntity> page = creativeSourceRepository.findByTitleOrBodyAndHostNot(title, desc, url,
                pageable);
        return page;
    }

    @Override
    public void save(List<CreativeSourceEntity> resultList) {
        creativeSourceRepository.save(resultList);
    }

    @Override
    public boolean exits(String id) {
        return creativeSourceRepository.exists(id);
    }

}
