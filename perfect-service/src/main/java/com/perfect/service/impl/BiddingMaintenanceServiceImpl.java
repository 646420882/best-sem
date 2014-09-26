package com.perfect.service.impl;

import com.perfect.dao.BiddingMaintenanceDAO;
import com.perfect.entity.UrlEntity;
import com.perfect.service.BiddingMaintenanceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by baizz on 2014-9-26.
 */
@Service("biddingMaintenanceService")
public class BiddingMaintenanceServiceImpl implements BiddingMaintenanceService {

    @Resource
    private BiddingMaintenanceDAO biddingMaintenanceDAO;

    @Override
    public void saveUrlEntity(UrlEntity urlEntity) {
        biddingMaintenanceDAO.save(urlEntity);
    }
}
