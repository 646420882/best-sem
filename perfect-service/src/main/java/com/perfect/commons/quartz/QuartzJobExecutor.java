package com.perfect.commons.quartz;

import com.perfect.commons.context.ApplicationContextHelper;
import com.perfect.dao.account.AccountManageDAO;
import com.perfect.db.mongodb.impl.AccountManageDAOImpl;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.service.MaterialsUploadService;
import com.perfect.service.impl.MaterialsUploadServiceImpl;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 2015-09-29.
 * <p>物料上传任务执行器.
 *
 * @author dolphineor
 */
@DisallowConcurrentExecution
public class QuartzJobExecutor implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzJobExecutor.class);

    private final MaterialsUploadService materialsUploadService;

    private final AccountManageDAO accountManageDAO;


    public QuartzJobExecutor() {
        materialsUploadService = ApplicationContextHelper.getBeanByClass(MaterialsUploadServiceImpl.class);
        accountManageDAO = ApplicationContextHelper.getBeanByClass(AccountManageDAOImpl.class);
    }


    /**
     * <p>
     * Called by the <code>{@link Scheduler}</code> when a <code>{@link Trigger}</code>
     * fires that is associated with the <code>Job</code>.
     * </p>
     *
     * @param context
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ScheduledJob scheduledJob = (ScheduledJob) context.getMergedJobDataMap().get("scheduledJob");
        List<Long> baiduUserIdList = accountManageDAO.getBaiduAccountItems(scheduledJob.getJobName()).stream()
                .map(BaiduAccountInfoDTO::getId).collect(Collectors.toList());
//        LOGGER.info("scheduledJob'name: {}, cronExpression: {}", scheduledJob.getJobName(), scheduledJob.getCronExpression());

        switch (scheduledJob.getJobType()) {
            case 10:
                upload(baiduUserIdList);
                break;
            case 11:
                pause(baiduUserIdList);
                break;
            default:
                break;
        }

    }

    /**
     * <p>上传分三种情况:
     * 1. <code>新增</code>(计划 单元 关键词 创意)
     * 2. <code>修改</code>(账户 计划 单元 关键词 创意)
     * 3. <code>删除</code>(计划 单元 关键词 创意)</p>
     *
     * @param baiduUserIdList 百度用户ID列表
     */
    private void upload(List<Long> baiduUserIdList) {
        baiduUserIdList.forEach(id -> {
            materialsUploadService.add(id);
            materialsUploadService.update(id);
            materialsUploadService.delete(id);
        });
    }

    /**
     * <p>暂停物料投放
     *
     * @param baiduUserIdList 百度用户ID列表
     */
    private void pause(List<Long> baiduUserIdList) {
        baiduUserIdList.forEach(materialsUploadService::pause);
    }
}
