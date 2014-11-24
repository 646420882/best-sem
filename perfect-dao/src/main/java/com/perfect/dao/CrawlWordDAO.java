package com.perfect.dao;

import com.perfect.entity.CrawlWordEntity;

import java.util.List;

/**
 * Created by baizz on 2014-11-18.
 * 2014-11-24 refactor
 */
public interface CrawlWordDAO extends MongoCrudRepository<CrawlWordEntity, String> {

    List<CrawlWordEntity> findBySite(String... sites);
}
