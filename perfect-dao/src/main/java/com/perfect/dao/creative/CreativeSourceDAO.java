package com.perfect.dao.creative;

import com.perfect.dto.creative.EsSearchResultDTO;

/**
 * Created by vbzer_000 on 2014/9/16.
 */
public interface CreativeSourceDAO {
    EsSearchResultDTO search(String query, int page, int size, int[] region);
}
