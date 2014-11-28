package com.perfect.service.impl;

import com.google.common.collect.Lists;
import com.perfect.dao.bidding.BiddingMaintenanceDAO;
import com.perfect.dto.UrlDTO;
import com.perfect.service.BiddingMaintenanceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by baizz on 2014-9-26.
 * 2014-11-26 refactor
 */
@Service("biddingMaintenanceService")
public class BiddingMaintenanceServiceImpl implements BiddingMaintenanceService {

    @Resource
    private BiddingMaintenanceDAO biddingMaintenanceDAO;

    @Override
    public List<UrlDTO> findAll() {
        return Lists.newArrayList(biddingMaintenanceDAO.findAll());
    }

    @Override
    public void saveUrlEntity(UrlDTO urlDTO) {
        biddingMaintenanceDAO.save(urlDTO);
    }
}
