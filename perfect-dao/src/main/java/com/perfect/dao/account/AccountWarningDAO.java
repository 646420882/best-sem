package com.perfect.dao.account;

import com.perfect.dto.WarningRuleDTO;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * Created by john on 2014/8/5.
 * 2014-11-28 refactor XiaoWei
 */
public interface AccountWarningDAO  {
    List<WarningRuleDTO> findEnableIsOne();

    void update(WarningRuleDTO warningRuleEntity);

    void updateMulti(Query query, Update update);

    List<WarningRuleDTO> findWarningRule(int isEnable, int isWarninged);

    Iterable<WarningRuleDTO> findByUserName(String user);

    void mySave(WarningRuleDTO warningRuleDTO);
}
