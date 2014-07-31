package com.perfect.app.homePage.service;



import com.perfect.dao.AccountAnalyzeDAO;
import com.perfect.entity.KeywordRealTimeDataVOEntity;
import com.perfect.mongodb.utils.Performance;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by SubDong on 2014/7/25.
 */
@Repository("performanceService")
public class PerformanceService{

    @Resource
    private AccountAnalyzeDAO accountAnalyzeDAO;

    /**
     * 账户表现中的分日表现数据
     * @param userTable
     * @param date
     * @return
     */
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
}


