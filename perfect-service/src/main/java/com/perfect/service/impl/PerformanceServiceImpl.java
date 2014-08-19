package com.perfect.service.impl;


import com.perfect.dao.AccountAnalyzeDAO;
import com.perfect.entity.AccountRealTimeDataVOEntity;
import com.perfect.entity.KeywordRealTimeDataVOEntity;
import com.perfect.mongodb.utils.Performance;
import com.perfect.service.PerformanceService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

/**
 * Created by SubDong on 2014/7/25.
 */
@Repository("performanceService")
public class PerformanceServiceImpl implements PerformanceService{

    @Resource
    private AccountAnalyzeDAO accountAnalyzeDAO;

    /**
     * 账户表现中的分日表现数据
     * @param userTable
     * @param date
     * @return
     */
    @Override
    public List<KeywordRealTimeDataVOEntity> performance(String userTable, String[] date){

        //首字母替换成大写
        String first = userTable.substring(0, 1).toUpperCase();
        String rest = userTable.substring(1, userTable.length());
        String newStr = new StringBuffer(first).append(rest).toString();

        Map<Long, KeywordRealTimeDataVOEntity> map = new HashMap<>();
        List<KeywordRealTimeDataVOEntity> analyzeEntities;
        List<KeywordRealTimeDataVOEntity> entities = new ArrayList<>();
        for (int i=0; i < date.length;i++){
            analyzeEntities =  accountAnalyzeDAO.performance(newStr+"-KeywordRealTimeData-log-"+date[i]);
            entities.addAll(analyzeEntities);
        }
        if(entities.size() != 0){
            ForkJoinPool joinPool = new ForkJoinPool();
            try {
                Future<Map<Long, KeywordRealTimeDataVOEntity>> joinTask =joinPool.submit(new Performance(entities, 0, entities.size()));
                map = joinTask.get();
                DecimalFormat df = new DecimalFormat("#.00");
                for(Map.Entry<Long,KeywordRealTimeDataVOEntity> entry : map.entrySet()){
                    if(entry.getValue().getImpression() == 0){
                        entry.getValue().setCtr(0.00);
                    }else{
                        entry.getValue().setCtr(Double.parseDouble(df.format(entry.getValue().getClick().doubleValue() / entry.getValue().getImpression().doubleValue())));
                    }
                    if(entry.getValue().getClick() == 0){
                        entry.getValue().setCpc(0.00);
                    }else{
                        entry.getValue().setCpc(Double.parseDouble(df.format(entry.getValue().getCost() / entry.getValue().getClick().doubleValue())));
                    }

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            joinPool.shutdown();
        }
        List<KeywordRealTimeDataVOEntity> list = new ArrayList<>(map.values());
        return list;
    }

    /**
     * 获取账户表现中的所有数据
     * @return
     */
    @Override
    public List<AccountRealTimeDataVOEntity> performanceUser(Date startDate, Date endDate,String fieldName,int Sorted,int limit){

        List<AccountRealTimeDataVOEntity> listUser = accountAnalyzeDAO.performaneUser(startDate,endDate,fieldName,Sorted,limit);
        DecimalFormat df = new DecimalFormat("#.00");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for(AccountRealTimeDataVOEntity list : listUser){
            String cpc = df.format(list.getCpc());
            String ctr = df.format(list.getCtr());

            BigDecimal ctrBig = new BigDecimal(ctr);
            BigDecimal big = new BigDecimal(100);
            double divide = ctrBig.multiply(big).doubleValue();
            double doubleCpc = Double.parseDouble(cpc);
            list.setCpc(doubleCpc);
            list.setCtr(divide);
        }
        return listUser;
    }

    /**
     * 获取账户表现中的曲线图所有数据
     * @return
     */
    @Override
    public List<AccountRealTimeDataVOEntity> performanceCurve(Date startDate,Date endDate){

        List<AccountRealTimeDataVOEntity> listUser = accountAnalyzeDAO.performaneCurve(startDate,endDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DecimalFormat df = new DecimalFormat("#.00");
        for(AccountRealTimeDataVOEntity list : listUser){
            String cpc = df.format(list.getCpc());
            String ctr = df.format(list.getCtr());
            BigDecimal ctrBig = new BigDecimal(ctr);
            BigDecimal big = new BigDecimal(100);
            double divide = ctrBig.multiply(big).doubleValue();
            double doubleCpc = Double.parseDouble(cpc);
            list.setCpc(doubleCpc);
            list.setCtr(divide);
        }
        return listUser;
    }
}


