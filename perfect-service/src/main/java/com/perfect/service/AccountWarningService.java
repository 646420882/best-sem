package com.perfect.service;

import com.perfect.dto.WarningRuleDTO;

import java.util.List;

/**
 * Created by XiaoWei on 2014/12/3.
 */
public interface AccountWarningService {
    void mySave(WarningRuleDTO warningRuleDTO);
    Iterable<WarningRuleDTO> findByUserName(String user);
    void update(WarningRuleDTO warningRuleEntity);
    List<WarningRuleDTO> findEnableIsOne();
    void save(WarningRuleDTO task);
    void updateMulti();
    List<WarningRuleDTO> findWarningRule(int isEnable, int isWarninged);
}
