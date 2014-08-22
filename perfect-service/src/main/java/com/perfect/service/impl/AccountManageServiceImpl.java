package com.perfect.service.impl;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.perfect.dao.AccountManageDAO;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.service.AccountManageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by baizz on 2014-8-21.
 */
@Service("accountManageService")
public class AccountManageServiceImpl implements AccountManageService<BaiduAccountInfoEntity> {

    @Resource
    private AccountManageDAO<BaiduAccountInfoEntity> accountManageDAO;

    public Map<String, Object> getAccountTree(BaiduAccountInfoEntity o) {
        ArrayNode treeNodes = accountManageDAO.getAccountTree(o);
        Map<String, Object> trees = new HashMap<>();
        trees.put("trees", treeNodes);
        return trees;
    }
}
