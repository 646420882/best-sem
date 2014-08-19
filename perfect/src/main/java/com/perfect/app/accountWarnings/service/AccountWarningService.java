package com.perfect.app.accountWarnings.service;

import com.perfect.dao.AccountWarningDAO;
import com.perfect.entity.WarningRuleEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by john on 2014/8/5.
 */
@Service("accountWarningService")
public class AccountWarningService {

    @Resource
    AccountWarningDAO accountWarningDAO;

    public void saveWarningRule(WarningRuleEntity warningRule) {
        accountWarningDAO.save(warningRule);
    }

    public Iterable<WarningRuleEntity> findAllWarningRule() {
        return accountWarningDAO.findAll();
    }

    public void updateWarningRuleOfIsEnbled(String id, Integer isEnbled) {
        WarningRuleEntity warningRuleEntity = new WarningRuleEntity();
        warningRuleEntity.setId(id);
        warningRuleEntity.setIsEnable(isEnbled);
        accountWarningDAO.save(warningRuleEntity);
    }
}
