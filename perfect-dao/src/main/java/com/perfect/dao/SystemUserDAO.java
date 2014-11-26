package com.perfect.dao;

import com.perfect.dto.SystemUserDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;

import java.util.List;

/**
 * Created by vbzer_000 on 2014/6/18.
 */
public interface SystemUserDAO extends MongoCrudRepository<SystemUserDTO, String> {

    /**
     * 根据用户名查询
     * <br>------------------------------<br>
     *
     * @param userName
     * @return
     */
    SystemUserDTO findByUserName(String userName);

    /**
     * 添加百度账户
     *
     * @param list
     * @param currSystemUserName
     */
    void addBaiduAccount(List<BaiduAccountInfoDTO> list, String currSystemUserName);

    void updateAccount(String userName);

    SystemUserDTO findByAid(long aid);

    void insertAccountInfo(String user, BaiduAccountInfoDTO baiduAccountInfoDTO);

    void removeAccountInfo(Long id);
}
