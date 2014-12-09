package com.perfect.dao.keyword;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.keyword.LexiconDTO;
import com.perfect.utils.paging.PagerInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-8-21.
 * 2014-12-2 refactor
 */
public interface KeywordGroupDAO extends HeyCrudRepository<LexiconDTO, String> {

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

    /**
     * 根据行业，关键词删除一条数据
     *
     * @param trade
     * @param keyword
     */
    void deleteByParams(String trade, String keyword);

    /**
     * 根据一些参数修改
     *
     * @param mapParams
     */
    void updateByParams(Map<String, Object> mapParams);
}
