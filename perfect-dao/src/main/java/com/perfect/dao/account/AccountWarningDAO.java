package com.perfect.dao.account;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.WarningRuleDTO;

import java.util.List;

/**
 * Created by john on 2014/8/5.
 * 2014-11-28 refactor XiaoWei
 */
public interface AccountWarningDAO extends HeyCrudRepository<WarningRuleDTO,Long> {
    List<WarningRuleDTO> findEnableIsOne();

    void update(WarningRuleDTO warningRuleEntity);

    List<WarningRuleDTO> findWarningRule(int isEnable, int isWarninged);

    Iterable<WarningRuleDTO> findByUserName(String user);

    void mySave(WarningRuleDTO warningRuleDTO);

    //scheduler 调用
    void updateMulti();

}
