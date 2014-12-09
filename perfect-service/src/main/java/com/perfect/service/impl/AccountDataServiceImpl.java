package com.perfect.service.impl;

import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.service.AccountDataService;
import com.perfect.service.SystemUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 获取账户完整数据的方法
 * 更新账户数据逻辑的方法
 * Created by yousheng on 2014/8/12.
 * 2014-12-2 refactor
 *
 * @author yousheng
 */
@Service("accountDataService")
public class AccountDataServiceImpl implements AccountDataService {

    @Resource
    private SystemUserService systemUserService;

    @Override
    public void initAccountData(String userName, Long accountId) {
        systemUserService.initAccount(userName, accountId);
    }

    @Override
    public void updateAccountData(String userName, Long accountId) {
        systemUserService.updateAccountData(userName, accountId);
    }

    @Override
    public void updateAccountData(String userName, Long accountId, List<Long> camIds) {
        systemUserService.updateAccountData(userName, accountId, camIds);
    }

    @Override
    public List<CampaignDTO> getCampaign(String userName, Long accountId) {
        return systemUserService.getCampaign(userName, accountId);
    }

}