package com.perfect.app.promotionAssistant.service;

import com.perfect.dao.AccountWarningDAO;
import com.perfect.entity.WarningRuleEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by john on 2014/8/5.
 */
@Service("accountWarningService")
public class AccountWarningService {

    @Resource
    AccountWarningDAO accountWarningDAO;

    public void saveWarningRule(WarningRuleEntity warningRule){
        accountWarningDAO.insert(warningRule);
    }

    public List<WarningRuleEntity> findAllWarningRule() {
        return accountWarningDAO.findAll();
    }

    public void updateWarningRuleOfIsEnbled(String id, Integer isEnbled) {
       WarningRuleEntity warningRuleEntity = new WarningRuleEntity();
        warningRuleEntity.setId(id);
        warningRuleEntity.setIsEnable(isEnbled);
        accountWarningDAO.update(warningRuleEntity);
    }
}
