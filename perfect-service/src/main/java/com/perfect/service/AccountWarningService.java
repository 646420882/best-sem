package com.perfect.service;

import com.perfect.dto.WarningRuleDTO;

/**
 * Created by XiaoWei on 2014/12/3.
 */
public interface AccountWarningService {
    void mySave(WarningRuleDTO warningRuleDTO);
    Iterable<WarningRuleDTO> findByUserName(String user);
    void update(WarningRuleDTO warningRuleEntity);
}
