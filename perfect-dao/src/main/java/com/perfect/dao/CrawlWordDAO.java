package com.perfect.dao;

import com.perfect.dto.CrawlWordDTO;

import java.util.List;

/**
 * Created by baizz on 2014-11-18.
 * 2014-11-26 refactor
 */
public interface CrawlWordDAO {

    List<CrawlWordDTO> findBySite(String... sites);
}
