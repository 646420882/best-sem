package com.perfect.schedule.task.execute;

import com.perfect.dao.AccountWarningDAO;
import com.perfect.dao.GetAccountReportDAO;
import com.perfect.dao.SystemUserDAO;
import com.perfect.dao.WarningInfoDAO;
import com.perfect.entity.AccountReportEntity;
import com.perfect.entity.SystemUserEntity;
import com.perfect.entity.WarningInfoEntity;
import com.perfect.entity.WarningRuleEntity;
import com.perfect.mongodb.utils.DateUtils;
import com.perfect.schedule.core.IScheduleTaskDealSingle;
import com.perfect.schedule.core.TaskItemDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by john on 2014/8/8.
 * 每天凌晨1点执行
 */
@Deprecated
public class CountYesterdayCostTask implements IScheduleTaskDealSingle<Map<String, Object>> {
    protected static transient Logger log = LoggerFactory.getLogger(IScheduleTaskDealSingle.class);

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Resource
    private SystemUserDAO systemUserDAO;
    @Resource
    private AccountWarningDAO accountWarningDAO;
    @Resource
    private GetAccountReportDAO getAccountReportDAO;
    @Resource
    private WarningInfoDAO warningInfoDAO;

    @Override
    public boolean execute(Map<String, Object> task, String ownSign) throws Exception {
        warningInfoDAO.insert((String) task.get("userName"), (WarningInfoEntity) task.get("value"));
        return true;
    }

    @Override
    public List<Map<String, Object>> selectTasks(String taskParameter, String ownSign, int taskItemNum, List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {

        //得到所有已经启用的的预警
        List<WarningRuleEntity> warningRuleList = accountWarningDAO.findEnableIsOne();
        //得到所有系统用户
        Iterable<SystemUserEntity> systemUserEntityList = systemUserDAO.findAll();

        List<Map<String, Object>> warningInfoList = new ArrayList<>();

        for (SystemUserEntity sue : systemUserEntityList) {
            if (warningRuleList.size() == 0) {
                break;
            }
            //昨天的日期
            Date yesterDay = DateUtils.getYesterday();
            //得到昨天的账户数据
            List<AccountReportEntity> accountYesTerdayDataList = getAccountReportDAO.getLocalAccountRealData(sue.getUserName(), yesterDay, yesterDay);

            for (WarningRuleEntity wre : warningRuleList) {
                for (AccountReportEntity art : accountYesTerdayDataList) {
                    WarningInfoEntity warningInfo = new WarningInfoEntity();
                    if (wre.getAccountId() == art.getAccountId()) {
                        //算出日预算实现率
                        double percent = (art.getPcCost().doubleValue() + art.getMobileCost().doubleValue()) / wre.getBudget() * 100;
                        warningInfo.setAccountId(wre.getAccountId());
                        warningInfo.setPercent(percent);
                        warningInfo.setCreateTime(new Date());
                        warningInfo.setBudget(wre.getBudget());

                        if (percent < 85) {
                            warningInfo.setWarningState("警示状态");
                            warningInfo.setSuggest("建议此类业务线立即优化、调整");
                            warningInfo.setWarningSign("red");
                        } else if (percent < 95) {
                            warningInfo.setWarningState("预警边缘");
                            warningInfo.setSuggest("建议此类业务线及时关注业务线变化，采取相应调整策略");
                            warningInfo.setWarningSign("yellow");
                        } else if (percent <= 100) {
                            warningInfo.setWarningState("发展状态良好");
                            warningInfo.setSuggest("可持续观察");
                            warningInfo.setWarningSign("green");
                        } else {
                            warningInfo.setWarningState("警示状态");
                            warningInfo.setSuggest("建议此类及时调整预算，预算不足时需及时申请加款");
                            warningInfo.setWarningSign("black");
                        }
                        Map<String, Object> map = new HashMap<>();
                        map.put("userName", sue.getUserName());
                        map.put("value", warningInfo);
                        warningInfoList.add(map);
                        break;
                    }
                }
            }
        }
        return warningInfoList;
    }

    @Override
    public Comparator<Map<String, Object>> getComparator() {
        return null;
    }
}
