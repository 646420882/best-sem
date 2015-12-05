package com.perfect.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.perfect.dao.account.MultipleAccountManageDAO;
import com.perfect.service.MultipleAccountManageService;

@Service("multipleAccountManageService")
public class MultipleAccountManageServiceImpl implements MultipleAccountManageService{
	
	@Resource
	private  MultipleAccountManageDAO multipleAccountManageDAO;
	
	@Override
	public Map<String, Object> getAccountDownloadstree(String currSystemUserName) {
        ArrayNode treeNodes = multipleAccountManageDAO.getAccountDownloadstree(currSystemUserName);
        Map<String, Object> trees = new HashMap<>();
        trees.put("trees", treeNodes);
        return trees;
	}

}
