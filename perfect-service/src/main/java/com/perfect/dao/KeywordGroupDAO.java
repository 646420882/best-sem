package com.perfect.dao;

import com.perfect.entity.LexiconEntity;

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
     * 加载行业库
     * @return
     */
    List<? extends Object> findTr();

    /**
     * 添加行业库数据
     * @param lexiconEntity
     */
    int saveTrade(LexiconEntity lexiconEntity);

    /**
     * 获取当前结果集的长度
     *
     * @param params
     * @return
     */
    int getCurrentRowsSize(Map<String, Object> params);
}
