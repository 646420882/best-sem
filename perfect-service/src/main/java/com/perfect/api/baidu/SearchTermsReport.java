package com.perfect.api.baidu;

import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.core.AppContext;
import com.perfect.service.AccountManageService;
import com.perfect.utils.BaiduServiceSupport;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by john on 2014/9/18.
 * 从百度上获取搜索词报告
 */

@Service
public class SearchTermsReport {

    @Resource
    private AccountManageService accountManageService;

    public List<RealTimeQueryResultType> getSearchTermsReprot(Integer levelOfDetails, Date startDate, Date endDate, List<AttributeType> attributes, Integer device, Integer searchType) {
        DateFormat df = new SimpleDateFormat("hh:mm:ss");
        try {
            Date baseDate = df.parse("11:51:00");
            Calendar beforeYesterDay = Calendar.getInstance();
            beforeYesterDay.add(Calendar.DAY_OF_YEAR, -2);//前天的日期
            Calendar yesterDay = Calendar.getInstance();
            yesterDay.add(Calendar.DAY_OF_YEAR, -1);//昨天的日期

            //若小于baseDate，则是11:51之前的,否则是11:51之后的,     请求时间在当天中午11:51前，startDate范围可取：[前天，前天-30] 请求时间在当天中午11:51后，startDate范围可取：[昨天，昨天-30]
            if (df.parse(df.format(startDate)).getTime() < baseDate.getTime()) {
                //若开始日期大于了前天，就将开始日期置为前天
                if (startDate.getTime() > beforeYesterDay.getTime().getTime()) {
                    startDate = beforeYesterDay.getTime();
                }
            } else {
                if (startDate.getTime() > yesterDay.getTime().getTime()) {
                    startDate = yesterDay.getTime();
                }
            }


            if (df.parse(df.format(endDate)).getTime() < baseDate.getTime()) {
                //若开始日期大于了前天，就将开始日期置为前天
                if (endDate.getTime() > beforeYesterDay.getTime().getTime()) {
                    endDate = beforeYesterDay.getTime();
                }
            } else {
                if (endDate.getTime() > yesterDay.getTime().getTime()) {
                    endDate = yesterDay.getTime();
                }
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

        CommonService commonService = BaiduServiceSupport.getCommonService(accountManageService.getBaiduAccountInfoById(AppContext.getAccountId()));
        ReportService reportService = null;
        try {
            reportService = commonService.getService(ReportService.class);
        } catch (ApiException e) {
            e.printStackTrace();
        }


        //设置请求参数
        RealTimeQueryRequestType realTimeQueryRequestType = new RealTimeQueryRequestType();
        String[] returnFileds = new String[]{"click", "impression"};
        realTimeQueryRequestType.setPerformanceData(Arrays.asList(returnFileds));
        realTimeQueryRequestType.setLevelOfDetails(levelOfDetails);
        realTimeQueryRequestType.setStartDate(startDate);
        realTimeQueryRequestType.setEndDate(endDate);
        realTimeQueryRequestType.setAttributes(attributes);
        realTimeQueryRequestType.setDevice(device);
        realTimeQueryRequestType.setReportType(6);//报告类型
        realTimeQueryRequestType.setNumber(20);//获取数据的条数


        //创建请求
        GetRealTimeQueryDataRequest getRealTimeQueryDataRequest = new GetRealTimeQueryDataRequest();
        getRealTimeQueryDataRequest.setRealTimeQueryRequestTypes(realTimeQueryRequestType);
        GetRealTimeQueryDataResponse response1 = reportService.getRealTimeQueryData(getRealTimeQueryDataRequest);

        if (response1 == null) {
            return new ArrayList<>();
        } else {
            List<RealTimeQueryResultType> resList = response1.getRealTimeQueryResultTypes();
            return resList;
        }
    }
}
