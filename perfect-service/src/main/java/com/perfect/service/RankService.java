package com.perfect.service;

import com.perfect.entity.RankEntity;

import java.util.List;

/**
 * Created by yousheng on 2014/8/19.
 *
 * @author yousheng
 */
public interface RankService {

    public void saveRank(RankEntity rankEntity);


    public void saveRanks(List<RankEntity> rankEntities);
}
