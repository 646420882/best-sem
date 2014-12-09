package com.perfect.api.baidu;

import com.perfect.utils.DateUtils;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by john on 2014-7-30.
 * 2014-11-26 refactor
 */
public class AccountRealTimeReport {

    public List<RealTimeResultType> getAccountRealTimeData(String username, String passwd, String token, String _startDate, String _endDate) {
        Date startDate = null, endDate = null;
        if (_startDate == null && _endDate == null) {
            startDate = DateUtils.getYesterday();
            endDate = startDate;
        } else {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-M-d");
            try {
                startDate = sd.parse(_startDate);
                endDate = sd.parse(_endDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        ReportService reportService = null;
        try {
            CommonService commonService = BaiduServiceSupport.getCommonService(username, passwd, token);
            if (commonService == null) {
                return new ArrayList<>();
            }
            reportService = commonService.getService(ReportService.class);
        } catch (ApiException e) {
            e.printStackTrace();
        }

        //设置请求参数
        RealTimeRequestType realTimeRequestType = new RealTimeRequestType();
        //指定返回数据类型
        realTimeRequestType.setPerformanceData(Arrays.asList("impression", "click", "ctr", "cost", "cpc", "conversion"));
        //指定起始时间
        realTimeRequestType.setStartDate(startDate);
        realTimeRequestType.setEndDate(endDate);
        //指定实时数据类型
        realTimeRequestType.setReportType(2);
        realTimeRequestType.setDevice(1);

        //创建请求
        GetRealTimeDataRequest getRealTimeDataRequest = new GetRealTimeDataRequest();
        getRealTimeDataRequest.setRealTimeRequestTypes(realTimeRequestType);
        GetRealTimeDataResponse response = null;
        if (reportService != null) {
            response = reportService.getRealTimeData(getRealTimeDataRequest);
        }
        if (response == null) {
            return new ArrayList<>();
        }
        return response.getRealTimeResultTypes();
    }
}
