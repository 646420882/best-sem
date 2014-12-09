package com.perfect.service.impl;

import com.perfect.dao.account.AccountWarningDAO;
import com.perfect.dto.WarningRuleDTO;
import com.perfect.service.AccountWarningService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by XiaoWei on 2014/12/3.
 */
@Service("accountWarningService")
public class AccountWarningServiceImpl implements AccountWarningService {
    @Resource
    private AccountWarningDAO accountWarningDAO;

    @Override
    public void mySave(WarningRuleDTO warningRuleDTO) {
        accountWarningDAO.mySave(warningRuleDTO);
    }

    @Override
    public Iterable<WarningRuleDTO> findByUserName(String user) {
        return accountWarningDAO.findByUserName(user);
    }

    @Override
    public void update(WarningRuleDTO warningRuleEntity) {
        accountWarningDAO.update(warningRuleEntity);
    }
}
