package com.perfect.service;

import com.perfect.dto.creative.CreativeSourceDTO;
import com.perfect.dto.creative.EsSearchResultDTO;

import java.util.List;

/**
 * Created by vbzer_000 on 2014/9/16.
 */
public interface CreativeSourceService {

    void save(CreativeSourceDTO creativeSourceDTO);

    void save(List<CreativeSourceDTO> resultList);

    boolean exits(String id);

    EsSearchResultDTO search(String query, int page, int size, int[] regions);
}
