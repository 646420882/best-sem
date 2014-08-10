package com.perfect.schedule.task.execute;

import com.perfect.api.baidu.RealTimeDataReports;
import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.sms.v3.RealTimeResultType;
import com.perfect.dao.SystemUserDAO;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.entity.SystemUserEntity;
import com.perfect.schedule.core.IScheduleTaskDealSingle;
import com.perfect.schedule.task.conf.TaskConfig;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by yousheng on 2014/8/7.
 *
 * @author yousheng
 */
@Component("reportDataTask")
public class ReportDataTask implements IScheduleTaskDealSingle<SystemUserEntity> {

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private SystemUserDAO systemUserDAO;


    @Override
    public boolean execute(SystemUserEntity task, String ownSign) throws Exception {

        List<BaiduAccountInfoEntity> baiduAccountInfoEntityList = task.getBaiduAccountInfoEntities();
        List<RealTimeResultType> realTimeResultTypeList = RealTimeDataReports.getUnitRealTimeDataPC(null, null);
        String dbname = "user_" + task.getUserName() + "_report";

        for (BaiduAccountInfoEntity entity : baiduAccountInfoEntityList) {
            String tokenId = entity.getToken();
            String username = entity.getBaiduUserName();
            String pwd = entity.getBaiduPassword();

            ServiceFactory sf = ServiceFactory.getInstance(username, pwd
                    , tokenId, null);


        }


        mongoTemplate.create
        return false;
    }


    /**
     *
     * @param taskParameter
     * @param ownSign
     * @param taskItemNum
     * @param taskItemList
     * @param eachFetchDataNum
     * @return
     * @throws Exception
     */
    @Override
    public List selectTasks(String taskParameter, String ownSign, int taskItemNum, List taskItemList, int eachFetchDataNum) throws Exception {
        if(ownSign == null || !ownSign.equals(TaskConfig.TASK_DOMAIN)){
            return Collections.emptyList();
        }

        List<SystemUserEntity> userEntityList = systemUserDAO.findAll();

        return userEntityList;
    }

    @Override
    public Comparator getComparator() {
        return null;
    }
}
