package com.perfect.service;

/**
 * Created on 2015-09-29.
 * <p>物料定时任务接口.
 *
 * @author dolphineor
 */
public interface MaterialsScheduledService {

    String MATERIALS_JOB_GROUP = "SEM_MATERIALS";


    /**
     * <p>配置定时任务
     *
     * @param jobType
     * @param jobLevel
     * @param jobContent
     * @param cronExpression
     */
    void configureScheduler(int jobType, int jobLevel, String[] jobContent, String cronExpression);

    /**
     * <p>判断定时任务是否已经存在
     *
     * @param jobType
     * @param jobLevel
     * @param jobContent
     * @return
     */
    boolean isExists(int jobType, int jobLevel, String[] jobContent);

    /**
     * 暂停任务
     *
     * @deprecated
     */
    void pauseJob(String jobId);

    /**
     * 恢复任务
     *
     * @deprecated
     */
    void resumeJob(String jobId);

    /**
     * 删除任务
     */
    void deleteJob(String jobId);


    /**
     * 上传本地新增物料并启用凤巢中暂停投放的关键词
     *
     * @param level 物料层级
     * @param ids   本地新增物料ID
     */
    void uploadAndStartMaterials(int level, String[] ids);

    /**
     * 暂停指定物料的投放
     *
     * @param level 物料层级
     * @param ids   凤巢物料ID(需要转换成{@code java.lang.Long})
     */
    void pauseMaterials(int level, String[] ids);
}
