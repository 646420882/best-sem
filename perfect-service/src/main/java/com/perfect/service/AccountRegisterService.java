package com.perfect.service;

/**
 * Created by SubDong on 2014/9/30.
 */
public interface AccountRegisterService {
    /**
     * 添加系统账户
     * @param account 帐号
     * @param pwd 密码
     * @param company 公司名称
     * @return
     */
    public int addAccount(String account, String pwd, String company,String email);
}
