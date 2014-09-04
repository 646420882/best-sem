package com.perfect.service.impl;

import com.perfect.dao.BiddingLogDAO;
import com.perfect.entity.bidding.BiddingLogEntity;
import com.perfect.service.BiddingLogService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by Administrator on 14-9-4.
 */
@Component
public class BiddingLogServiceImpl implements BiddingLogService {

    @Resource
    private BiddingLogDAO biddingLogDAO;

    @Override
    public void save(BiddingLogEntity biddingLogEntity) {
        biddingLogDAO.save(biddingLogEntity);
    }
}
