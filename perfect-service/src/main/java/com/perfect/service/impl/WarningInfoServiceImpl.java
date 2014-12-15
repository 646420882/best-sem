package com.perfect.service.impl;

import com.perfect.dao.WarningInfoDAO;
import com.perfect.dto.WarningInfoDTO;
import com.perfect.service.WarningInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by SubDong on 2014/12/15.
 */
@Service("warningInfoService")
public class WarningInfoServiceImpl implements WarningInfoService{
    @Resource
    private WarningInfoDAO warningInfoDAO;
    @Override
    public void insert(String userName, WarningInfoDTO warningInfoEntity) {
        warningInfoDAO.insert(userName,warningInfoEntity);
    }
}
