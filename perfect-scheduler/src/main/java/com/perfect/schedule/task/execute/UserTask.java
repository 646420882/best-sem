package com.perfect.schedule.task.execute;

import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.dao.AccountDAO;
import com.perfect.dao.AdgroupDAO;
import com.perfect.dao.CampaignDAO;
import com.perfect.dao.SystemUserDAO;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.entity.SystemUserEntity;
import com.perfect.schedule.core.IScheduleTaskDealSingle;
import com.perfect.schedule.core.TaskItemDefine;
import com.perfect.schedule.utils.AccountDataUpdateTask;
import com.perfect.schedule.utils.WorkPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by vbzer_000 on 2014/6/18.
 */

@Component("userUpdateTask")
public class UserTask implements IScheduleTaskDealSingle<SystemUserEntity> {
    protected static transient Logger log = LoggerFactory.getLogger(IScheduleTaskDealSingle.class);

    @Resource
    private AccountDAO accountDAO;

    @Resource
    private CampaignDAO campaignDAO;

    @Resource
    private SystemUserDAO systemUserDAO;

    @Resource
    private AdgroupDAO adgroupDAO;

    @Resource

    @Override
    public boolean execute(SystemUserEntity systemUser, String ownSign) throws Exception {

        List<BaiduAccountInfoEntity> baiduAccountInfoList = systemUser.getBaiduAccountInfoEntities();

        if (baiduAccountInfoList == null) {
            return false;
        }

        for (BaiduAccountInfoEntity baiduAccountInfo : baiduAccountInfoList) {

            AccountDataUpdateTask accountDataUpdateTask = new AccountDataUpdateTask(ServiceFactory.getInstance(baiduAccountInfo.getBaiduUserName(), baiduAccountInfo.getBaiduPassword(), baiduAccountInfo.getToken(), null), accountDAO, campaignDAO, adgroupDAO);

            WorkPool.pushTask(accountDataUpdateTask);

        }

        return true;
    }

    @Override
    public List selectTasks(String taskParameter, String ownSign, int taskItemNum, List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {

        List<SystemUserEntity> result = new ArrayList<>();

        List<SystemUserEntity> systemUsers = systemUserDAO.findAll();
        return systemUsers;
    }

    @Override
    public Comparator getComparator() {
        return null;
    }
}
