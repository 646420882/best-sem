package com.perfect.service.impl;

import com.perfect.dao.RankDAO;
import com.perfect.entity.RankEntity;
import com.perfect.service.RankService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yousheng on 2014/8/19.
 *
 * @author yousheng
 */
@Service
public class RankServiceImpl implements RankService {

    @Resource
    private RankDAO rankDAO;

    @Override
    public void saveRank(RankEntity rankEntity) {

    }

    @Override
    public void saveRanks(List<RankEntity> rankEntities) {

    }
}
