package com.perfect.dao;


import com.perfect.dto.WarningInfoDTO;

/**
 * Created by john on 2014/8/8.
 * 2014-11-28 refactor XiaoWei
 */
public interface WarningInfoDAO{
     void insert(String userName,WarningInfoDTO warningInfoEntity);
}
