package com.perfect.dao;

import com.perfect.entity.CensusEntity;

/**
 * Created by XiaoWei on 2014/11/11.
 */
public interface CensusDAO extends  MongoCrudRepository<CensusEntity,Long> {
    CensusEntity saveParams(CensusEntity censusEntity);
}
