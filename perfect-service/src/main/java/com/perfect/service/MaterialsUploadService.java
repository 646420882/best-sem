package com.perfect.service;

import com.perfect.commons.deduplication.KeywordDeduplication;
import com.perfect.dto.creative.CreativeDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created on 2015-10-14.
 * <p>物料上传接口.
 *
 * @author dolphineor
 */
public interface MaterialsUploadService {

    /**
     * 重复
     */
    int DUPLICATED = 0;

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
     * <p>上传新增
     * 新增的部分包括: 推广计划、推广单元、关键词、创意,
     * 上传时的顺序为: 计划->单元->关键词、创意
     * </p>
     *
     * @param baiduUserId 百度账号id
     * @return {@code true} if upload success, otherwise, return {@code false}.
     */
    boolean add(Long baiduUserId);

    /**
     * <p>上传修改
     * 修改的部分包括: 账户、推广计划、推广单元、关键词、创意
     * </p>
     *
     * @param baiduUserId
     * @return
     */
    boolean update(Long baiduUserId);

    /**
     * <p>上传删除
     * 删除的部分包括: 推广计划、推广单元、关键词、创意
     * </p>
     *
     * @param baiduUserId
     * @return
     */
    boolean delete(Long baiduUserId);

    /**
     * <p>上传指定百度账户下物料的新增、修改、删除</p>
     *
     * @param baiduUserId
     * @return
     */
    Map<Integer, Long> upload(Long baiduUserId);

    /**
     * <p>上传指定系统用户下物料的新增、修改、删除</p>
     *
     * @param sysUser
     * @return 上传失败的百度账号
     */
    Map<Integer, Set<Long>> upload(String sysUser);

    /**
     * <p>暂停指定百度账户下的物料投放</p>
     *
     * @param baiduUserId
     */
    boolean pause(Long baiduUserId);

    /**
     * <p>暂停指定系统用户下的所有百度账户物料投放</p>
     *
     * @param sysUser
     * @return 上传失败的百度账号
     */
    List<Long> pause(String sysUser);


    default boolean isDuplicate(CreativeDTO source, List<CreativeDTO> targets) {
        String sourceMd5 = KeywordDeduplication.MD5
                .getMD5(source.getTitle() + source.getDescription1() + source.getDescription2());

        return targets.stream()
                .filter(c -> Long.compare(source.getAdgroupId(), c.getAdgroupId()) == 0)
                .anyMatch(c -> sourceMd5.equals(KeywordDeduplication.MD5
                        .getMD5(c.getTitle() + c.getDescription1() + c.getDescription2())));
    }
}
