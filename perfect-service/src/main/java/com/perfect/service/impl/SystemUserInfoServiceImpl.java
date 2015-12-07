package com.perfect.service.impl;

import com.perfect.account.BaseBaiduAccountInfoVO;
import com.perfect.account.SystemUserInfoVO;
import com.perfect.core.AppContext;
import com.perfect.service.SystemUserInfoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Created on 2015-12-07.
 *
 * @author dolphineor
 */
@Service("systemUserInfoService")
public class SystemUserInfoServiceImpl implements SystemUserInfoService {

    @Override
    public List<SystemUserInfoVO> findAllSystemUserAccount() {
        // TODO 查询所有的系统用户, 需要用户中心提供接口
        return null;
    }

    @Override
    public SystemUserInfoVO findSystemUserInfoByUserName(String username) {
        // TODO 根据系统用户名查询用户的全部信息, 需要用户中心提供接口
        return null;
    }

    @Override
    public BaseBaiduAccountInfoVO findByBaiduUserId(Long baiduUserId) {
        for (BaseBaiduAccountInfoVO baiduAccountInfoVO : AppContext.getBaiduAccounts()) {
            if (Objects.equals(baiduUserId, baiduAccountInfoVO.getAccountId())) {
                return baiduAccountInfoVO;
            }
        }

        return null;
    }

    @Override
    public List<BaseBaiduAccountInfoVO> findBaiduAccountsByUserName(String username) {
        return findSystemUserInfoByUserName(username).getBaiduAccounts();
    }

    @Override
    public SystemUserInfoVO findSystemUserInfoByBaiduAccountId(Long baiduUserId) {
        // TODO 根据百度账号ID查询系统用户信息, 需要用户中心提供接口
        return null;
    }

    @Override
    public List<BaseBaiduAccountInfoVO> findAllBaiduAccounts() {
        // TODO 有待实现
        return null;
    }
}
