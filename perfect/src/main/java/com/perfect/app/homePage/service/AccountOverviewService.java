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
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    //当前登录用户名
    private static String currLoginUserName;
    static {
        currLoginUserName = (currLoginUserName == null) ? CustomUserDetailsService.getUserName() : currLoginUserName;
    }

    /**
     * 汇总。。。。
     * @return
     */
    public  Map<String,Object> getKeyWordSum(Integer days,String startDate,String endDate){
        //各种汇总初始化
        long impressionCount = 0 ;
        long clickCount = 0 ;
        double costCount = 0.0;
        double conversionCount = 0.0;
        Date start = null;
        Date end = null;
        try {
            start = !"".equals(startDate)?df.parse(startDate):null;
             end =  !"".equals(endDate)?df.parse(endDate):null;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //当前登录用户名首字母大写
        currLoginUserName =currLoginUserName.replaceFirst(currLoginUserName.substring(0, 1), currLoginUserName.substring(0, 1).toUpperCase());

        //昨天
        if(days!=null && days == 1){
            try {
                end = new Date(df.parse(df.format(new Date())).getTime()-(1000 * 60 * 60 * 24));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        //选择的一个时间段（startDate至endDate）
        if( start!=null && end!=null ) {
            days = new Long((end.getTime()-start.getTime())/(1000 * 60 * 60 * 24)).intValue()+1;
        }

        Date date = end==null?new Date():end;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

       for(int i = 1;i<=days;i++) {
           String userTable = currLoginUserName + table + df.format(cal.getTime());
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
           cal.add(Calendar.DATE, -1);
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
