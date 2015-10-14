package com.perfect.service;

/**
 * Created on 2015-10-14.
 * <p>物料上传接口.
 *
 * @author dolphineor
 */
public interface MaterialsUploadService {

    /**
     * <p>上传新增
     * 新增的部分包括: 推广计划、推广单元、关键词、创意,
     * 上传时的顺序为: 计划->单元->关键词、创意
     * </p>
     *
     * @param baiduUserId
     * @return
     */
    boolean uploadAdditions(Long baiduUserId);

    /**
     * <p>上传修改
     * 修改的部分包括: 账户、推广计划、推广单元、关键词、创意
     * </p>
     *
     * @param baiduUserId
     * @return
     */
    boolean uploadModifications(Long baiduUserId);

    /**
     * <p>上传删除
     * 删除的部分包括: 推广计划、推广单元、关键词、创意
     * </p>
     *
     * @param baiduUserId
     * @return
     */
    boolean uploadDeletions(Long baiduUserId);


    /**
     * <p>暂停物料投放
     *
     * @param baiduUserId
     */
    void pause(Long baiduUserId);
}
