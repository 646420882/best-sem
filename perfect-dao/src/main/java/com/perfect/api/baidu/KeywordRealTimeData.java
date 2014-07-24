package com.perfect.api.baidu;

import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.mongodb.utils.BaseBaiduService;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by baizz on 14-7-24.
 */
public class KeywordRealTimeData {

    private static CommonService service = BaseBaiduService.getCommonService();

    public static List<RealTimeResultType> getKeywordRealTimeData(List<Long> keywordIds, String _startDate, String _endDate) {
        if (_startDate == null && _endDate != null) {
            Assert.notNull(_startDate, "_startDate must not be null!");
        }
        if (_endDate == null && _startDate != null) {
            Assert.notNull(_endDate, "_endDate must not be null!");
        }

        Date startDate = null, endDate = null;
        if (_startDate == null && _endDate == null) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            cal.add(Calendar.DATE, -1);
            startDate = cal.getTime();
            endDate = cal.getTime();
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
            reportService = service.getService(ReportService.class);
        } catch (ApiException e) {
            e.printStackTrace();
        }

        //设置请求参数
        RealTimeRequestType realTimeRequestType = new RealTimeRequestType();
        //关键词统计范围下的id集合
        realTimeRequestType.setStatIds(keywordIds);
        //指定返回数据类型
        realTimeRequestType.setPerformanceData(Arrays.asList(new String[]{"impression", "click", "cost", "cpc", "conversion"}));
        //指定起始时间
        realTimeRequestType.setStartDate(startDate);
        realTimeRequestType.setEndDate(endDate);
        //指定返回的数据层级
        realTimeRequestType.setLevelOfDetails(11);
        //指定实时数据类型
        realTimeRequestType.setReportType(14);
        //指定统计范围
        realTimeRequestType.setStatRange(11);
        realTimeRequestType.setDevice(1);

        //创建请求
        GetRealTimeDataRequest getRealTimeDataRequest = new GetRealTimeDataRequest();
        getRealTimeDataRequest.setRealTimeRequestTypes(realTimeRequestType);
        GetRealTimeDataResponse response = reportService.getRealTimeData(getRealTimeDataRequest);
        List<RealTimeResultType> list = response.getRealTimeResultTypes();
        return list;
    }

}
