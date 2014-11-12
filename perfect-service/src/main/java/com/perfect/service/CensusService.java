package com.perfect.service;

import com.perfect.dao.MongoCrudRepository;
import com.perfect.dto.ConstantsDTO;
import com.perfect.entity.CensusEntity;
import com.perfect.mongodb.base.BaseMongoTemplate;
import com.perfect.service.impl.CensusServiceImpl;

/**
 * Created by XiaoWei on 2014/11/11.
 */
public interface CensusService extends MongoCrudRepository<CensusEntity,Long> {
    /**
     * 参数组，添加方法
     * @param osAnBrowser
     * @return
     */
    String saveParams(String[] osAnBrowser);

    /**
     * 查询今日数据
     * @return
     */
    public ConstantsDTO getTodayTotal();
}
