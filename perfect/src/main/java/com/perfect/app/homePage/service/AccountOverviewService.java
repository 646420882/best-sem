package com.perfect.app.homePage.service;

import com.mongodb.DBObject;
import com.perfect.api.baidu.AccountRealTimeData;
import com.perfect.dao.AccountAnalyzeDAO;
import com.perfect.entity.AccountRealTimeDataVOEntity;
import com.perfect.entity.KeywordRealTimeDataVOEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by john on 2014/7/25.
 */
@Service("accountOverviewService")
public class AccountOverviewService {

    @Resource
    private AccountAnalyzeDAO accountAnalyzeDAO;

    private  DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 汇总。。。。
     * @return
     */
    public  Map<String,Object> getKeyWordSum(String startDate,String endDate){
        //各种汇总初始化
        long impressionCount = 0 ;
        long clickCount = 0 ;
        double costCount = 0.0;
        double conversionCount = 0.0;


         //开始获取数据汇总
        List<AccountRealTimeDataVOEntity> list = null;
        try {
            list = accountAnalyzeDAO.performaneCurve(df.parse(startDate),df.parse(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (AccountRealTimeDataVOEntity ard : list) {
               Integer impression = ard.getImpression();
               Integer click = ard.getClick();
               Double cost = ard.getCost();
               Double conversion = ard.getConversion();

               if (impression != null) {
                   impressionCount += impression;
               }

               if (click != null) {
                   clickCount += click;
               }

               if (cost != null) {
                   costCount += cost;
               }

               if (conversion != null) {
                   conversionCount += conversion;
               }
           }

        //数字格式化
        DecimalFormat decimalFormat = new DecimalFormat("#,##,###");
        Map<String,Object> map = new Hashtable<String,Object>();
        map.put("impression",decimalFormat.format(impressionCount));
        map.put("click",decimalFormat.format(clickCount));
        map.put("cos",decimalFormat.format((long)costCount));
        map.put("conversion",decimalFormat.format((long)conversionCount));
        return map;
    }

}
