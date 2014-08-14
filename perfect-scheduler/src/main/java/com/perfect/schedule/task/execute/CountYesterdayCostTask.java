package com.perfect.schedule.task.execute;

import com.perfect.dao.AccountWarningDAO;
import com.perfect.dao.GetRealTimeDataDAO;
import com.perfect.dao.SystemUserDAO;
import com.perfect.dao.WarningInfoDAO;
import com.perfect.entity.AccountRealTimeDataVOEntity;
import com.perfect.entity.SystemUserEntity;
import com.perfect.entity.WarningInfoEntity;
import com.perfect.entity.WarningRuleEntity;
import com.perfect.schedule.core.IScheduleTaskDealSingle;
import com.perfect.schedule.core.TaskItemDefine;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by john on 2014/8/8.
 */
public class CountYesterdayCostTask implements IScheduleTaskDealSingle<Map<String,Object>> {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat  dayDF= new SimpleDateFormat("yyyy-MM-dd 23:59:59");


    @Resource
    private SystemUserDAO systemUserDAO;
    @Resource
    private AccountWarningDAO accountWarningDAO;
    @Resource
    private GetRealTimeDataDAO getRealTimeDataDAO;
    @Resource
    private WarningInfoDAO warningInfoDAO;

    @Override
    public boolean execute(Map<String,Object> task, String ownSign) throws Exception {
        warningInfoDAO.insert((String)task.get("userName"),(WarningInfoEntity)task.get("value"));
        return true;
    }

    @Override
    public List<Map<String,Object>> selectTasks(String taskParameter, String ownSign, int taskItemNum, List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
        Date everyDayStart = df.parse(df.format(new Date()));
        Date everyDayEnd = dayDF.parse(dayDF.format(new Date()));

        //得到所有已经启用的的预警
        List<WarningRuleEntity> warningRuleList = accountWarningDAO.find(new Query().addCriteria(Criteria.where("isEnable").is(1).and("dayCountDate").not().gte(everyDayStart).lte(everyDayEnd)),WarningRuleEntity.class);
        //得到所有用户
        List<SystemUserEntity> systemUserEntityList = systemUserDAO.findAll();

        List<Map<String,Object>> warningInfoList = new ArrayList<>();

        for(SystemUserEntity sue:systemUserEntityList){

            if(warningRuleList.size()==0){
                break;
            }

            //昨天的日期
            Date yesterDay = new Date(df.parse(df.format(new Date())).getTime()-(1000*60*60*24));
            //得到昨天的账户数据
            //List<AccountRealTimeDataVOEntity> accountYesTerdayDataList = getRealTimeDataDAO.getLocalAccountRealData(sue.getUserName(),df.parse("2014-02-02"), df.parse("2014-02-02"));
            List<AccountRealTimeDataVOEntity> accountYesTerdayDataList = getRealTimeDataDAO.getLocalAccountRealData(sue.getUserName(),yesterDay,yesterDay);

            for(WarningRuleEntity wre:warningRuleList){
                for(AccountRealTimeDataVOEntity art:accountYesTerdayDataList){
                    WarningInfoEntity warningInfo = new WarningInfoEntity();
                    if(wre.getAccountId().longValue()==art.getAccountId().longValue()){
                        //算出日预算实现率
                        double percent = art.getCost()/wre.getBudget()*100;
                        warningInfo.setAccountId(wre.getAccountId());
                        warningInfo.setPercent(percent);
                        warningInfo.setCreateTime(new Date());
                        warningInfo.setBudget(wre.getBudget());

                        if(percent<85){
                            warningInfo.setWarningState("警示状态");
                            warningInfo.setSuggest("建议此类业务线立即优化、调整");
                            warningInfo.setWarningSign("red");
                        }else if(percent<95){
                            warningInfo.setWarningState("预警边缘");
                            warningInfo.setSuggest("建议此类业务线及时关注业务线变化，采取相应调整策略");
                            warningInfo.setWarningSign("yellow");
                        }else if(percent<=100){
                            warningInfo.setWarningState("发展状态良好");
                            warningInfo.setSuggest("可持续观察");
                            warningInfo.setWarningSign("green");
                        }else{
                            warningInfo.setWarningState("警示状态");
                            warningInfo.setSuggest("建议此类及时调整预算，预算不足时需及时申请加款");
                            warningInfo.setWarningSign("black");
                        }


                        Map<String,Object> map = new HashMap<>();
                        map.put("userName",sue.getUserName());
                        map.put("value",warningInfo);
                        warningInfoList.add(map);

                        //修改是否预警的状态和日统计时间
                            wre.setIsWarninged(0);
                            wre.setDayCountDate(new Date());
                            accountWarningDAO.update(wre);
                        break;
                    }
                }
            }
        }
        return warningInfoList;
    }

    @Override
    public Comparator<Map<String,Object>> getComparator() {
        return null;
    }
}
