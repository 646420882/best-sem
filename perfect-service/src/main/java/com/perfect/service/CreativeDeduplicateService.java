package com.perfect.service;

import com.perfect.dto.creative.CreativeDTO;

import java.util.List;
import java.util.Map;

/**
 * Created on 2015-10-23.
 *
 * @author dolphineor
 */
public interface CreativeDeduplicateService {

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
     * <p>全账户去重.
     * Map ->
     * Key: 重复的创意
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
     * <p>同一单元去重.
     *
     * @param baiduUserId 百度账户ID
     * @param adgroupId   推广单元ID
     * @return
     */
    Map<Integer, List<String>> deduplicate(final Long baiduUserId, final Long adgroupId);

    /**
     * <p>添加创意时, 对同一单元创意去重, 对于重复的创意对其设定相应的状态.
     *
     * @param baiduUserId 百度账户ID
     * @param adgroupId   推广单元ID
     * @param list        待处理创意
     * @return List带重复标识的创意
     */
    List<CreativeDTO> deduplicate(final Long baiduUserId, final Long adgroupId, final List<CreativeDTO> list);

    /**
     * <p>添加创意时, 对同一单元创意去重, 对于重复的创意对其设定相应的状态.
     *
     * @param baiduUserId 百度账户ID
     * @param adgroupId   本地推广单元ID
     * @param list        待处理创意
     * @return List带重复标识的创意
     */
    List<CreativeDTO> deduplicate(final Long baiduUserId, final String adgroupId, final List<CreativeDTO> list);
}
