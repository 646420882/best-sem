package com.perfect.service;

import com.perfect.dto.SystemUserDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;

/**
 * Created by yousheng on 2014/8/12.
 *
 * @author yousheng
 */
public interface SystemUserService {

    public SystemUserDTO getSystemUser(String userName);

    public SystemUserDTO getSystemUser(long aid);

    Iterable<SystemUserDTO> getAllUser();

    public void save(SystemUserDTO systemUserDTO);

    boolean removeAccount(Long id);

    void addAccount(String user, BaiduAccountInfoDTO baiduAccountInfoDTO);

    boolean updatePassword(String userName, String pwd);
}
