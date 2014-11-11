package com.perfect.service;

import com.perfect.dao.MongoCrudRepository;
import com.perfect.entity.CensusEntity;
import com.perfect.mongodb.base.BaseMongoTemplate;

/**
 * Created by XiaoWei on 2014/11/11.
 */
public interface CensusService extends MongoCrudRepository<CensusEntity,Long> {
    String saveParams(String[] osAnBrowser);
}
