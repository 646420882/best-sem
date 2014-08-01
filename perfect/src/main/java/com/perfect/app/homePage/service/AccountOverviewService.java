package com.perfect.app.homePage.service;

import com.mongodb.DBObject;
import com.perfect.dao.AccountAnalyzeDAO;
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
    private static final String table = "-KeywordRealTimeData-log-";

    /**
     * 汇总。。。。
     * @return
     */
    public  Map<String,Object> getKeyWordSum(String currLoginUserName,List<String> dates){
        //各种汇总初始化
        long impressionCount = 0 ;
        long clickCount = 0 ;
        double costCount = 0.0;
        double conversionCount = 0.0;

        //当前登录用户名首字母大写
        currLoginUserName =currLoginUserName.replaceFirst(currLoginUserName.substring(0, 1), currLoginUserName.substring(0, 1).toUpperCase());


         //开始获取数据汇总
        for(String date:dates){
           String userTable = currLoginUserName + table + date;
           List<KeywordRealTimeDataVOEntity> list = accountAnalyzeDAO.performance(userTable);
           System.out.println("已经统计的表："+userTable);

           for (KeywordRealTimeDataVOEntity krt : list) {
               Integer impression = krt.getImpression();
               Integer click = krt.getClick();
               Double cost = krt.getCost();
               Double conversion = krt.getConversion();

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
