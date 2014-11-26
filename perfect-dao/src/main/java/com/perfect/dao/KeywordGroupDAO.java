package com.perfect.dao;

import com.perfect.dto.keyword.LexiconDTO;
import com.perfect.utils.PagerInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-8-21.
 */
public interface KeywordGroupDAO extends MongoCrudRepository<LexiconDTO, Long> {

    /**
     * 查询行业词库下的类别
     *
     * @param trade
     * @return
     */
    List<? extends Object> findCategories(String trade);

    /**
     * 加载行业库
     *
     * @return
     */
    List<? extends Object> findTr();

    /**
     * 添加行业库数据
     *
     * @param lexiconDTO
     */
    int saveTrade(LexiconDTO lexiconDTO);

    /**
     * 获取当前结果集的长度
     *
     * @param params
     * @return
     */
    int getCurrentRowsSize(Map<String, Object> params);

    /**
     * 分页查询行业库
     *
     * @param params 查询参数
     * @param page
     * @param limit
     * @return
     */
    PagerInfo findByPager(Map<String, Object> params, int page, int limit);
}
