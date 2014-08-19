package com.perfect.schedule.task.execute;

import com.perfect.autosdk.sms.v3.RealTimeResultType;
import com.perfect.dao.AccountWarningDAO;
import com.perfect.dao.GetRealTimeDataDAO;
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
 * Created by john on 2014/8/6.
 */
public class SenderTask implements IScheduleTaskDealSingle<WarningRuleEntity> {

    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Resource
    private AccountWarningDAO accountWarningDAO;
    @Resource
    private GetRealTimeDataDAO getRealTimeDataDAO;

    @Override
    public boolean execute(WarningRuleEntity task, String ownSign) throws Exception {


        Map<String, String[]> map = new HashMap<>();
        String[] tels = task.getTels().split(";");
        String[] mails = task.getMails().split(";");
        map.put("tels", tels);
        map.put("mails", mails);
//        WorkPool.pushTask(new Sender(map));

        //将当天预警过得预警规则的状态修改为已预警过
        task.setIsWarninged(1);
        accountWarningDAO.save(task);
        return true;
    }

    @Override
    public List<WarningRuleEntity> selectTasks(String taskParameter, String ownSign, int taskItemNum, List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {

        List<WarningRuleEntity> executeList = new ArrayList<>();

        //得到已经启用并且当天没有预警过的
        List<WarningRuleEntity> warningRuleList = accountWarningDAO.find(new Query().addCriteria(Criteria.where("isEnable").is(1).and("isWarninged").is(0)), WarningRuleEntity.class);

        //得到当天的账户实时数据
        List<RealTimeResultType> todayAccountRealDataList = getRealTimeDataDAO.getAccountRealTimeTypeByDate(df.format(new Date()), df.format(new Date()));
        // List<RealTimeResultType> todayAccountRealDataList = getRealTimeDataDAO.getAccountRealTimeTypeByDate("2014-02-02", "2014-02-02");

        for (WarningRuleEntity wre : warningRuleList) {
            //根据不同的比例和预算金额算出当天消费的金额
            double cost = wre.getWarningPercent() / 100 * wre.getBudget();
            for (RealTimeResultType rtr : todayAccountRealDataList) {
                if (wre.getAccountId().longValue() == rtr.getID().longValue()) {
                    if (Double.parseDouble(rtr.getKPI(3)) >= cost) {
                        executeList.add(wre);
                    }
                    break;
                }
            }
        }
        return executeList;
    }

    @Override
    public Comparator<WarningRuleEntity> getComparator() {
        return null;
    }


}
