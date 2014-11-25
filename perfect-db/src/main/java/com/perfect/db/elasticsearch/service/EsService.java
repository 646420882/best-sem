package com.perfect.db.elasticsearch.service;

/**
 * Created by vbzer_000 on 2014/9/16.
 */
public interface EsService {
    com.perfect.dto.EsSearchResultDTO search(String query, int page, int size, int[] region);
}
