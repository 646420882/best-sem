package com.perfect.tasks;

import com.perfect.dao.AccountWarningDAO;
import com.perfect.dao.GetAccountReportDAO;
import com.perfect.dao.SystemUserDAO;
import com.perfect.dao.WarningInfoDAO;
import com.perfect.entity.*;
import com.perfect.mongodb.utils.DateUtils;
import org.quartz.JobExecutionException;
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
public class EverdayWarningTask {
    protected static transient Logger log = LoggerFactory.getLogger(EverdayWarningTask.class);

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Resource
    private SystemUserDAO systemUserDAO;
    @Resource
    private AccountWarningDAO accountWarningDAO;
    @Resource
    private GetAccountReportDAO getAccountReportDAO;
    @Resource
    private WarningInfoDAO warningInfoDAO;


    public boolean execute(List<Map<String, Object>> tasks) throws Exception {

        for (Map<String, Object> task : tasks) {
            warningInfoDAO.insert((String) task.get("userName"), (WarningInfoEntity) task.get("value"));
        }
        return true;
    }


    public List<Map<String, Object>> selectTasks() throws Exception {

        //得到所有已经启用的的预警
        List<WarningRuleEntity> warningRuleList = accountWarningDAO.findEnableIsOne();
        //得到所有系统用户
        Iterable<SystemUserEntity> systemUserEntityList = systemUserDAO.findAll();

        List<Map<String, Object>> warningInfoList = new ArrayList<>();

        //若没有预警规则，无需预警
        if (warningRuleList.size() == 0) {
            return warningInfoList;
        }

        for (SystemUserEntity sue : systemUserEntityList) {

            if(sue.getState().longValue()==0){
                continue;
            }
            if(sue.getBaiduAccountInfoEntities()==null || sue.getBaiduAccountInfoEntities().size()==0){
                continue;
            }
            if("administrator".equals(sue.getUserName())){
                continue;
            }

            //昨天的日期
            Date yesterDay = DateUtils.getYesterday();
//            Date yesterDay = new SimpleDateFormat("yyyy-MM-dd").parse("2014-09-24");

            //得到昨天的账户数据
            AccountReportEntity accountYesTerdayData = getAccountReportDAO.getLocalAccountRealData(sue.getUserName(), yesterDay,yesterDay);

            for (WarningRuleEntity wre : warningRuleList) {
                    if(validateBaiduUserState(sue.getBaiduAccountInfoEntities(),wre.getAccountId())==false){
                        continue;
                    }

                    if (accountYesTerdayData!=null && wre.getAccountId() == accountYesTerdayData.getAccountId().longValue()) {
                        WarningInfoEntity warningInfo = new WarningInfoEntity();
                        //算出日预算实现率
                        double percent = (accountYesTerdayData.getPcCost().doubleValue() + accountYesTerdayData.getMobileCost().doubleValue()) / wre.getBudget() * 100;
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
        return warningInfoList;
    }


    /**
     * 验证系统用户下的百度账户是否可用
     * @return
     */
    private boolean validateBaiduUserState(List<BaiduAccountInfoEntity> list,long accountId){
        if(list==null){
            return false;
        }

        for(BaiduAccountInfoEntity baiduUser : list){
               if(baiduUser.getId().longValue()==accountId){
                    if(baiduUser.getState()==0){
                        return false;
                    }else{
                        return true;
                    }
               }
        }
        return false;
    }


    /**
     * 启动该任务
     *
     * @throws JobExecutionException
     */
    public void startTask() throws JobExecutionException {
        try {
            List<Map<String, Object>> warningInfoList = selectTasks();
            execute(warningInfoList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
