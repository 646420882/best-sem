package com.perfect.service;

import com.perfect.commons.deduplication.Md5Helper;
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
     * <p>上传新增
     * 新增的部分包括: 推广计划、推广单元、关键词、创意,
     * 上传时的顺序为: 计划->单元->关键词、创意
     * </p>
     *
     * @param baiduAccountId 百度账号id
     * @return {@code true} if upload success, otherwise, return {@code false}.
     */
    @Deprecated
    boolean add(Long baiduAccountId);

    /**
     * <p>上传修改
     * 修改的部分包括: 账户、推广计划、推广单元、关键词、创意
     * </p>
     *
     * @param baiduAccountId
     * @return
     */
    @Deprecated
    boolean update(Long baiduAccountId);

    /**
     * <p>上传删除
     * 删除的部分包括: 推广计划、推广单元、关键词、创意
     * </p>
     *
     * @param baiduAccountId
     * @return
     */
    @Deprecated
    boolean delete(Long baiduAccountId);

    /**
     * <p>上传指定百度账户下物料的新增、修改、删除</p>
     *
     * @param baiduAccountId
     * @return
     */
    @Deprecated
    Map<Integer, Long> upload(Long baiduAccountId);

    /**
     * <p>上传指定系统用户下物料的新增、修改、删除</p>
     *
     * @param sysUser
     * @return 上传失败的百度账号
     */
    @Deprecated
    Map<Integer, Set<Long>> upload(String sysUser);

    /**
     * <p>暂停指定百度账户下的物料投放</p>
     *
     * @param baiduAccountId
     */
    @Deprecated
    boolean pause(Long baiduAccountId);

    /**
     * <p>暂停指定系统用户下的所有百度账户物料投放</p>
     *
     * @param sysUser
     * @return 上传失败的百度账号
     */
    @Deprecated
    List<Long> pause(String sysUser);


    // ==========================================================

    /**
     * <p>
     * 1. 上传本地新增物料
     * 2. 启动凤巢中指定层级下暂停投放的物料
     * </p>
     *
     * @param userName       系统用户名
     * @param baiduAccountId 凤巢帐号ID
     * @param level          物料层级
     * @param materialsObjId 本地新增物料的Mongo ID
     * @return
     */
    boolean uploadAndStartMaterials(String userName, long baiduAccountId, int level, String[] materialsObjId);

    /**
     * <p>暂停凤巢指定层级下正在进行投放的物料</p>
     *
     * @param userName       系统用户名
     * @param baiduAccountId 凤巢帐号ID
     * @param level          物料层级
     * @return
     */
    boolean pauseMaterials(String userName, long baiduAccountId, int level);
    // ==========================================================


    default boolean isDuplicate(CreativeDTO source, List<CreativeDTO> targets) {
        String sourceMd5 = Md5Helper.MD5
                .getMD5(source.getTitle() + source.getDescription1() + source.getDescription2());

        return targets.stream()
                .filter(c -> Long.compare(source.getAdgroupId(), c.getAdgroupId()) == 0)
                .anyMatch(c -> sourceMd5.equals(Md5Helper.MD5
                        .getMD5(c.getTitle() + c.getDescription1() + c.getDescription2())));
    }
}
