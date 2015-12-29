package com.perfect.commons.quartz;

import com.perfect.commons.context.ApplicationContextHelper;
import com.perfect.service.MaterialsScheduledService;
import com.perfect.service.MaterialsUploadService;
import com.perfect.service.impl.MaterialsScheduledServiceImpl;
import com.perfect.service.impl.MaterialsUploadServiceImpl;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Created on 2015-09-29.
 * <p>物料上传任务执行器.
 *
 * @author dolphineor
 */
@DisallowConcurrentExecution
public class QuartzJobExecutor implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzJobExecutor.class);

    private final MaterialsScheduledService materialsScheduledService;

    private final MaterialsUploadService materialsUploadService;


    public QuartzJobExecutor() {
        materialsUploadService = ApplicationContextHelper.getBean("materialsUploadService", MaterialsUploadServiceImpl.class);
        materialsScheduledService = ApplicationContextHelper.getBean("materialsScheduledService", MaterialsScheduledServiceImpl.class);
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
        String sysUserName = scheduledJob.getJobName();
        long baiduAccountId = Long.parseLong(scheduledJob.getJobName().split(":")[1]);

        switch (scheduledJob.getJobType()) {
            // 物料上传
            // 1. 上传本地新增物料
            // 2. 启动凤巢中指定层级下暂停投放的物料
            case 10: {
                LOGGER.info("==========开始上传 {} 的物料==========", sysUserName);
                // 提取物料层级
                int level = scheduledJob.getJobLevel();
                String[] jobContent = scheduledJob.getJobContent();
                boolean result = materialsUploadService.uploadAndStartMaterials(sysUserName, baiduAccountId, level, jobContent);
                if (!result) {
                    LOGGER.info("ID为 {} 的百度帐号物料上传失败", baiduAccountId);
                }

                LOGGER.info("========== {} 的物料上传结束==========", sysUserName);
                break;
            }
            // 物料暂停
            // 暂停凤巢指定层级下正在进行投放的物料
            case 11: {
                // 提取物料层级
                int level = scheduledJob.getJobLevel();
                boolean result = materialsUploadService.pauseMaterials(sysUserName, baiduAccountId, level);
                if (!result) {
                    LOGGER.info("系统用户 {} 下, ID为 {} 的百度帐号暂停物料投放失败", sysUserName, baiduAccountId);
                }

                break;
            }
            default:
                break;
        }

        if (Objects.isNull(context.getNextFireTime())) {
            materialsScheduledService.deleteJob(scheduledJob.getJobId());
        }
    }

}
