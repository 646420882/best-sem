package com.perfect.app.homePage.service;

import com.perfect.dao.AccountAnalyzeDAO;
import com.perfect.entity.KeywordRealTimeDataVOEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by XiaoWei on 2014/7/29.
 */
@Repository("importKeywordService")
public class ImportKeywordService {
    @Resource
    AccountAnalyzeDAO accountAnalyzeDAO;
    private final String DATE_START="startDate";
    private final String DATE_END="endDate";
    private final String USER_NAME="userTable";

    /**
     * 根据时间，用户账号获取最近关键字信息
     * @param request
     * @return
     */
    public List<KeywordRealTimeDataVOEntity> getList(HttpServletRequest request) {
        Date[] currDate=getDate(request);
        List<KeywordRealTimeDataVOEntity> lst = new ArrayList<>();
        String newStr=request.getParameter(USER_NAME);
        String newStr_new= getUpCaseWord(newStr);
        if(currDate.length>0){
            for (int i = 0; i < currDate.length; i++) {
                String tableName = (newStr_new + "-KeywordRealTimeData-log-" + currDate[i]);
                List<KeywordRealTimeDataVOEntity> list = accountAnalyzeDAO.performance(tableName);
                lst.addAll(list);
            }
        }
        return lst;
    }

    /**
     * 获取时间段
     * @param request
     * @return
     */
    private Date[] getDate(HttpServletRequest request){
        Date[] date=null;
        String d_start=request.getParameter(DATE_START);
        String d_end=request.getParameter(DATE_END);
        if (StringUtils.isNotEmpty(d_start)&&StringUtils.isNotEmpty(d_end)){
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            try {
                date[0]=sdf.parse(d_start);
                date[1]=sdf.parse(d_end);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }
    private String getUpCaseWord(String str){
        if (str.length()>1){
            String first = str.substring(0, 1).toUpperCase();
            String rest = str.substring(1, str.length());
            return new StringBuffer(first).append(rest).toString();
        }
        return str;
    }
}
