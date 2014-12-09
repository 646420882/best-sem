package com.perfect.dao.sys;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.CrawlWordDTO;

import java.util.List;

/**
 * Created by baizz on 2014-11-18.
 * 2014-12-2 refactor
 */
public interface CrawlWordDAO extends HeyCrudRepository<CrawlWordDTO, String> {

    List<CrawlWordDTO> findBySite(String... sites);
}
