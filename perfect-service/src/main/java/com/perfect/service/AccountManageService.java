package com.perfect.service;

import java.util.Map;

/**
 * Created by baizz on 2014-8-21.
 */
public interface AccountManageService<T> {

    /**
     * 获取账户树
     *
     * @param t
     * @return
     */
    Map<String, Object> getAccountTree(T t);
}
