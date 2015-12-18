package com.perfect.dao.account;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.sys.ModuleAccountInfoDTO;
import com.perfect.dto.sys.SystemUserDTO;

import java.util.List;

public interface MultipleAccountManageDAO extends HeyCrudRepository<SystemUserDTO, String> {

    /**
     * 多账户树
     *
     * @param currSystemUserName
     * @return
     */
    ArrayNode getAccountDownloadstree(String currSystemUserName);

    /**
     * 获取百度帐户列表
     *
     * @return
     */
    List<ModuleAccountInfoDTO> getBaiduAccountItems(String currUserName);

}
