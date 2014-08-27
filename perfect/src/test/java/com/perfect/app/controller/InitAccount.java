package com.perfect.app.controller;

import com.perfect.service.AccountDataService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by baizz on 14-8-27.
 */
public class InitAccount extends JUnitBaseController  {

    @Autowired
    private AccountDataService accountDataService;

    public void setAccountDataService(@Qualifier("accountDataService") AccountDataService accountDataService) {
        this.accountDataService = accountDataService;
    }

    @Test
    public void init(){
        accountDataService.initAccountData("shangpin", 2565730l);
    }
}
