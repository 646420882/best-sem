package com.perfect.dao;

import com.perfect.dto.ConstantsDTO;
import com.perfect.entity.CensusEntity;

/**
 * Created by XiaoWei on 2014/11/11.
 */
public interface CensusDAO extends  MongoCrudRepository<CensusEntity,Long> {
    /**
     * 添加数据源
     * @param censusEntity
     * @return
     */
    CensusEntity saveParams(CensusEntity censusEntity);

    /**
     * 根据今日访问数据
     * @return
     */
    ConstantsDTO getTodayTotal();
}
