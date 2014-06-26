package com.perfect.schedule.task;

import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.service.BaiduServiceSupport;
import com.perfect.autosdk.sms.v3.AccountInfoType;
import com.perfect.autosdk.sms.v3.AccountService;
import com.perfect.autosdk.sms.v3.GetAccountInfoRequest;
import com.perfect.autosdk.sms.v3.GetAccountInfoResponse;
import com.perfect.mongodb.dao.AccountDAO;
import com.perfect.mongodb.dao.SystemUserDAO;
import com.perfect.mongodb.entity.BaiduAccountInfo;
import com.perfect.mongodb.entity.SystemUser;
import com.perfect.schedule.core.IScheduleTaskDealSingle;
import com.perfect.schedule.core.TaskItemDefine;
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
public class UserTask implements IScheduleTaskDealSingle<SystemUser> {
    protected static transient Logger log = LoggerFactory.getLogger(IScheduleTaskDealSingle.class);

    private static CommonService service = BaiduServiceSupport.getService();

    @Resource
    private AccountDAO accountDAO;

    @Resource
    private SystemUserDAO systemUserDAO;

    @Override
    public boolean execute(SystemUser systemUser, String ownSign) throws Exception {

        List<BaiduAccountInfo> baiduAccountInfoList = systemUser.getBaiduAccountInfos();

        if(baiduAccountInfoList == null ){
            return false;
        }

        for (BaiduAccountInfo baiduAccountInfo : baiduAccountInfoList) {
            AccountService accountService = ServiceFactory.getInstance(baiduAccountInfo.getBaiduUserName(), baiduAccountInfo.getBaiduPassword(), baiduAccountInfo.getToken(), null).getService(AccountService.class);

            GetAccountInfoRequest getAccountInfoRequest = new GetAccountInfoRequest();
            GetAccountInfoResponse getAccountInfoResponse = accountService.getAccountInfo(getAccountInfoRequest);

            AccountInfoType accountInfoType = getAccountInfoResponse.getAccountInfoType();
            accountDAO.updateById(accountInfoType);
        }

        return true;
    }

    @Override
    public List selectTasks(String taskParameter, String ownSign, int taskItemNum, List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {

        List<SystemUser> result = new ArrayList<>();

        List<SystemUser> systemUsers = systemUserDAO.findAll();
        return systemUsers;
    }

    @Override
    public Comparator getComparator() {
        return null;
    }
}
