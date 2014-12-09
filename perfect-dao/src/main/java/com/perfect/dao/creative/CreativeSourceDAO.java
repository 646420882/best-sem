package com.perfect.dao.creative;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.creative.CreativeSourceDTO;
import com.perfect.dto.creative.EsSearchResultDTO;

/**
 * Created by vbzer_000 on 2014/9/16.
 */
public interface CreativeSourceDAO extends HeyCrudRepository<CreativeSourceDTO, String> {
    EsSearchResultDTO search(String query, int page, int size, int[] region);

}
