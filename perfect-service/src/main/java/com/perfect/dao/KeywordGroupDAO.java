package com.perfect.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-8-21.
 */
public interface KeywordGroupDAO {

    /**
     * 查询行业词库下的类别
     *
     * @param trade
     * @return
     */
    List<? extends Object> findCategories(String trade);

    /**
     * 获取当前结果集的长度
     *
     * @param params
     * @return
     */
    int getCurrentRowsSize(Map<String, Object> params);
}
