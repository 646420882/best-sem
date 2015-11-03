package com.perfect.service;

import com.perfect.dto.keyword.KeywordDTO;

import java.util.List;
import java.util.Map;

/**
 * Created on 2015-10-23.
 *
 * @author dolphineor
 */
public interface KeywordDeduplicateService {

    /**
     * 重复
     */
    int DUPLICATED = -1;

    /**
     * 新增
     */
    int NEW = 1;

    /**
     * 修改
     */
    int MODIFIED = 2;

    /**
     * 删除
     */
    int DELETED = 3;


    /**
     * <p><code>上传</code>时全账户去重.
     * Map ->
     * Key: 重复的关键词
     * Value: {
     *   Key: NEW MODIFIED
     *   Value: List中的值为: "推广计划名称:推广单元名称"</p>
     * }
     *
     * @param baiduUserId 百度账户ID
     * @return
     */
    Map<String, Map<Integer, List<String>>> deduplicate(final Long baiduUserId);

    /**
     * <p><code>上传</code>时同一单元去重.
     * Map ->
     * Key: NEW MODIFIED
     * Value: List中的值为: "推广计划名称:推广单元名称"</p>
     *
     * @param baiduUserId 百度账户ID
     * @param adgroupId   推广单元ID
     * @return
     */
    Map<Integer, List<String>> deduplicate(final Long baiduUserId, final Long adgroupId);

    /**
     * <p>本地<code>批量添加</code>关键词时, 全账户去重.
     *
     * @param baiduUserId 百度账户ID
     * @param newKeywords 批量添加的关键词名称
     * @return 重复关键词的MongoDB ID
     */
    List<String> deduplicate(final Long baiduUserId, final List<String> newKeywords);

    /**
     * <p>添加关键词时, 对同一单元关键词去重, 对于重复的关键词对其设定相应的状态.
     *
     * @param baiduUserId 百度账户ID
     * @param adgroupId   推广单元ID
     * @param list        待处理关键词
     * @return List带重复标识的关键词
     */
    List<KeywordDTO> deduplicate(final Long baiduUserId, final Long adgroupId, final List<KeywordDTO> list);
}
