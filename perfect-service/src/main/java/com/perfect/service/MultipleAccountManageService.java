package com.perfect.service;

import java.util.Map;

public interface MultipleAccountManageService {
	
	/**
     *  获取多账户树
     * @param currSystemUserName
     * @return
     */
    Map<String, Object> getAccountDownloadstree(String currSystemUserName);

}
