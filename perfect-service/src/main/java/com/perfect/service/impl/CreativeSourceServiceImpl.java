package com.perfect.service.impl;

import com.perfect.dao.creative.CreativeSourceDAO;
import com.perfect.dto.creative.CreativeSourceDTO;
import com.perfect.dto.creative.EsSearchResultDTO;
import com.perfect.service.CreativeSourceService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by vbzer_000 on 2014/9/16.
 */
@Component("creativeSourceService")
public class CreativeSourceServiceImpl implements CreativeSourceService {

    @Resource
    private CreativeSourceDAO creativeSourceRepository;

    @Override
    public void save(CreativeSourceDTO creativeSourceEntity) {
        creativeSourceRepository.save(creativeSourceEntity);
    }

    @Override
    public void save(List<CreativeSourceDTO> resultList) {
        creativeSourceRepository.save(resultList);
    }

    @Override
    public boolean exits(String id) {
        return creativeSourceRepository.exists(id);
    }

    @Override
    public EsSearchResultDTO search(String query, int page, int size, int[] regions) {
        return creativeSourceRepository.search(query, page, size, regions);
    }

}
