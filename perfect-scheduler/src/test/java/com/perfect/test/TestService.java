package com.perfect.test;

import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.sms.v3.AccountInfoType;
import com.perfect.autosdk.sms.v3.AccountService;
import com.perfect.autosdk.sms.v3.GetAccountInfoRequest;
import com.perfect.autosdk.sms.v3.GetAccountInfoResponse;
import com.perfect.dao.AccountDAO;
import com.perfect.dao.SystemUserDAO;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.entity.SystemUserEntity;
import com.perfect.schedule.core.IScheduleTaskDealSingle;
import com.perfect.schedule.core.TaskItemDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by vbzer_000 on 2014/6/18.
 */


public class TestService implements IScheduleTaskDealSingle<SystemUserEntity> {
    protected static transient Logger log = LoggerFactory.getLogger(IScheduleTaskDealSingle.class);

    private static CommonService service = null;

    @Resource
    private AccountDAO accountDAO;

    @Resource
    private SystemUserDAO systemUserDAO;

    @Override
    public boolean execute(SystemUserEntity systemUser, String ownSign) throws Exception {

//        SystemUser systemUser = mongoTemplate.findById(userId, SystemUser.class);

        List<BaiduAccountInfoEntity> baiduAccountInfoList = systemUser.getBaiduAccountInfoEntities();

        for (BaiduAccountInfoEntity baiduAccountInfo : baiduAccountInfoList) {
            AccountService accountService = ServiceFactory.getInstance(baiduAccountInfo.getBaiduUserName(), baiduAccountInfo.getBaiduPassword(), baiduAccountInfo.getToken(), null).getService(AccountService.class);

            GetAccountInfoRequest getAccountInfoRequest = new GetAccountInfoRequest();
            GetAccountInfoResponse getAccountInfoResponse = accountService.getAccountInfo(getAccountInfoRequest);

            AccountInfoType accountInfoType = getAccountInfoResponse.getAccountInfoType();
//            accountDAO.update(accountInfoType, accountInfoType);
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
