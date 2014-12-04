package com.perfect.service;

import com.perfect.dto.CrawlWordDTO;

/**
 * Created by baizz on 2014-12-4.
 * 2014-12-4 refactor
 */
public interface CrawlWordService {

    default void save(Iterable<CrawlWordDTO> crawlWordDTOs) {

    }
}
