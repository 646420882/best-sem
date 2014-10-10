package com.perfect.tasks;

import com.perfect.autosdk.sms.v3.RealTimeResultType;
import com.perfect.dao.AccountWarningDAO;
import com.perfect.dao.GetAccountReportDAO;
import com.perfect.entity.WarningRuleEntity;
import com.perfect.schedule.utils.WorkPool;
import com.perfect.transmitter.sender.Sender;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by john on 2014/8/6.
 * 每隔十分钟执行
 */
public class SenderWarningTask {

    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    private Date dateSign = new Date();

    @Resource
    private AccountWarningDAO accountWarningDAO;
    @Resource
    private GetAccountReportDAO getAccountReportDAO;

    {
        try {
            dateSign = df.parse(df.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public boolean execute(List<WarningRuleEntity> tasks) throws Exception {

        for (WarningRuleEntity task : tasks) {
            Map<String, Object> map = new HashMap<>();
            String[] mails = task.getMails().split(",");
            map.put("tels", task.getTels());
            map.put("mails", mails);
            WorkPool.pushTask(new Sender(map));
            //将当天预警过得预警规则的状态修改为已预警过
            task.setIsWarninged(1);
            accountWarningDAO.save(task);
        }

        return true;
    }


    public List<WarningRuleEntity> selectTasks() throws Exception {
        //若到了第二天，是否预警的状态重新初始化为未预警
        Date currentDate = df.parse(df.format(new Date()));
        if (currentDate.getTime() > dateSign.getTime()) {
            Query query = new Query().addCriteria(Criteria.where("isEnable").is(1));
            Update update = new Update();
            update.set("isWarninged", 0);
            accountWarningDAO.updateMulti(query, update);
            dateSign = currentDate;
        }

        List<WarningRuleEntity> executeList = new ArrayList<>();
        //得到已经启用并且当天没有预警过的
        List<WarningRuleEntity> warningRuleList = accountWarningDAO.findWarningRule(1, 0);

        for (WarningRuleEntity wre : warningRuleList) {
            //得到当天的账户实时数据
            List<RealTimeResultType> todayAccountRealDataList = getAccountReportDAO.getAccountRealTimeTypeByDate(wre.getAccountId(), df.format(new Date()), df.format(new Date()));
            //根据不同的比例和预算金额算出当天消费的金额
            double cost = wre.getWarningPercent() / 100 * wre.getBudget();
            for (RealTimeResultType rtr : todayAccountRealDataList) {
                if (wre.getAccountId() == rtr.getID().longValue()) {
                    if (Double.parseDouble(rtr.getKPI(3)) >= cost) {
                        executeList.add(wre);
                    }
                    break;
                }
            }
        }
        return executeList;
    }


    /**
     * 开始执行该任务
     */
    public void startTask() {
        try {
            List<WarningRuleEntity> executeList = selectTasks();
            execute(executeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
