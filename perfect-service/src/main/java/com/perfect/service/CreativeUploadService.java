package com.perfect.service;

import java.util.List;
import java.util.Map;

/**
 * Created on 2015-10-23.
 *
 * @author dolphineor
 */
public interface CreativeUploadService {

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
     * @param baiduUserId
     * @return
     */
    Map<String, Map<Integer, List<String>>> deduplicate(Long baiduUserId);

    /**
     * <p>同一单元去重.
     *
     * @param baiduUserId
     * @param adgroupId
     * @return
     */
    Map<Integer, List<String>> deduplicate(Long baiduUserId, Long adgroupId);
}
