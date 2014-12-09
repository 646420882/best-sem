package com.perfect.test.app.controller;

import com.google.common.collect.Lists;
import com.perfect.core.AppContext;
import com.perfect.service.AccountDataService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by baizz on 2014-8-27.
 * <p>
 * 初始化和更新帐户数据
 */
public class AccountDataManageControllerTest extends JUnitBaseControllerTest {

    @Autowired
    private AccountDataService accountDataService;

    public void setAccountDataService(@Qualifier("accountDataService") AccountDataService accountDataService) {
        this.accountDataService = accountDataService;
    }

    @Test
    public void initAccount() {
        AppContext.setUser("perfect", 7001963l);
        accountDataService.initAccountData("perfect", 7001963l);
    }

    @Test
    public void updateAccount() {
        AppContext.setUser("perfect", 6243012l);
        accountDataService.updateAccountData("perfect", 6243012l);
    }

    @Test
    public void updateCampaign() {
        AppContext.setUser("perfect", 7001963l);
        accountDataService.updateAccountData("perfect", 7001963l, Lists.newArrayList(19055559l));
    }
}
