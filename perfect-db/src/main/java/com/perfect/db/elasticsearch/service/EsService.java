package com.perfect.db.elasticsearch.service;

import com.perfect.dto.creative.EsSearchResultDTO;

/**
 * Created by vbzer_000 on 2014/9/16.
 */
public interface EsService {
    EsSearchResultDTO search(String query, int page, int size, int[] region);
}
