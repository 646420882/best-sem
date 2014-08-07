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
 * Created by SubDong on 2014/8/7.
 */
public class RealTimeDataReports {

    //得到百度aip
    private static CommonService service = BaseBaiduService.getCommonService();

    private static  Date startDate = null;

    private static Date endDate = null;

    private static ReportService reportService = null;

    /**
     * 初始化API中需要共用到的属性
     * @param _startDate 开始时间
     * @param _endDate 结束时间
     */
    private static void processingTime(String _startDate, String _endDate){
        if (_startDate == null) {
            Assert.notNull(_startDate, "_startDate must not be null!");
        }
        if (_endDate == null) {
            Assert.notNull(_endDate, "_endDate must not be null!");
        }
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
        try {
            reportService = service.getService(ReportService.class);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取账户数据
     * @param _startDate
     * @param _endDate
     * @return
     */
    public static List<RealTimeResultType> getAccountRealTimeData(String _startDate, String _endDate){
        //初始化时间
        processingTime(_startDate,_endDate);

        //得到设置返回数据工厂
        RealTimeRequestType requestType = new RealTimeRequestType();
        //指定返回数据类型
        requestType.setPerformanceData(Arrays.asList(new String[]{"impression", "click", "ctr", "cost", "cpc", "conversion"}));

         // 指定返回的数据层级
         // 默认为账户
         // 2：账户粒度 3：计划粒度 5：单元粒度 7：创意粒度 11：关键词(keywordid)粒度 12：关键词(keywordid)+创意粒度 6：关键词(wordid)粒度
        requestType.setLevelOfDetails(2);
        //指定起始时间
        requestType.setStartDate(startDate);
        requestType.setEndDate(endDate);
        //设置实时数据类型
        //2：账户 10：计划 11：单元 14：关键词(keywordid) 12：创意 15：配对 3：地域 9：关键词(wordid)
        requestType.setReportType(2);

        //创建访问百度接口请求
        GetRealTimeDataRequest dataRequest = new GetRealTimeDataRequest();
        dataRequest.setRealTimeRequestTypes(requestType);
        GetRealTimeDataResponse dataResponse =  reportService.getRealTimeData(dataRequest);
        List<RealTimeResultType> list = dataResponse.getRealTimeResultTypes();
        return list;
    }

    /**
     * 获取关键词PC端数据
     * @param _startDate
     * @param _endDate
     * @return
     */
    public static List<RealTimeResultType> getKeyWordidRealTimeDataPC(String _startDate, String _endDate){
        //初始化时间
        processingTime(_startDate,_endDate);

        //得到设置返回数据工厂
        RealTimeRequestType requestType = new RealTimeRequestType();
        //指定返回数据类型
        requestType.setPerformanceData(Arrays.asList(new String[]{"impression", "click", "ctr", "cost", "cpc", "position", "conversion"}));

        // 指定返回的数据层级
        // 默认为账户
        // 2：账户粒度 3：计划粒度 5：单元粒度 7：创意粒度 11：关键词(keywordid)粒度 12：关键词(keywordid)+创意粒度 6：关键词(wordid)粒度
        requestType.setLevelOfDetails(11);
        //指定起始时间
        requestType.setStartDate(startDate);
        requestType.setEndDate(endDate);
        //设置实时数据类型
        //2：账户 10：计划 11：单元 14：关键词(keywordid) 12：创意 15：配对 3：地域 9：关键词(wordid)
        requestType.setReportType(14);
        // 设置搜索推广渠道
        // 0：全部搜索推广设备  1：仅计算机 2：仅移动
        requestType.setDevice(1);

        //创建访问百度接口请求
        GetRealTimeDataRequest dataRequest = new GetRealTimeDataRequest();
        dataRequest.setRealTimeRequestTypes(requestType);
        GetRealTimeDataResponse dataResponse =  reportService.getRealTimeData(dataRequest);
        List<RealTimeResultType> list = dataResponse.getRealTimeResultTypes();
        return list;
    }

    /**
     * 获取关键词移动端数据
     * @param _startDate
     * @param _endDate
     * @return
     */
    public static List<RealTimeResultType> getKeyWordidRealTimeDataMobile(String _startDate, String _endDate){
        //初始化时间
        processingTime(_startDate,_endDate);

        //得到设置返回数据工厂
        RealTimeRequestType requestType = new RealTimeRequestType();
        //指定返回数据类型
        requestType.setPerformanceData(Arrays.asList(new String[]{"impression", "click", "ctr", "cost", "cpc", "position", "conversion"}));

        // 指定返回的数据层级
        // 默认为账户
        // 2：账户粒度 3：计划粒度 5：单元粒度 7：创意粒度 11：关键词(keywordid)粒度 12：关键词(keywordid)+创意粒度 6：关键词(wordid)粒度
        requestType.setLevelOfDetails(11);
        //指定起始时间
        requestType.setStartDate(startDate);
        requestType.setEndDate(endDate);
        //设置实时数据类型
        //2：账户 10：计划 11：单元 14：关键词(keywordid) 12：创意 15：配对 3：地域 9：关键词(wordid)
        requestType.setReportType(14);
        // 设置搜索推广渠道
        // 0：全部搜索推广设备  1：仅计算机 2：仅移动
        requestType.setDevice(2);

        //创建访问百度接口请求
        GetRealTimeDataRequest dataRequest = new GetRealTimeDataRequest();
        dataRequest.setRealTimeRequestTypes(requestType);
        GetRealTimeDataResponse dataResponse =  reportService.getRealTimeData(dataRequest);
        List<RealTimeResultType> list = dataResponse.getRealTimeResultTypes();
        return list;
    }
    /**
     * 获取单元PC端数据
     * @param _startDate
     * @param _endDate
     * @return
     */
    public static List<RealTimeResultType> getUnitRealTimeDataPC(String _startDate, String _endDate){
        //初始化时间
        processingTime(_startDate,_endDate);

        //得到设置返回数据工厂
        RealTimeRequestType requestType = new RealTimeRequestType();
        //指定返回数据类型
        requestType.setPerformanceData(Arrays.asList(new String[]{"impression", "click", "ctr", "cost", "cpc", "position", "conversion"}));

        // 指定返回的数据层级
        // 默认为账户
        // 2：账户粒度 3：计划粒度 5：单元粒度 7：创意粒度 11：关键词(keywordid)粒度 12：关键词(keywordid)+创意粒度 6：关键词(wordid)粒度
        requestType.setLevelOfDetails(5);
        //指定起始时间
        requestType.setStartDate(startDate);
        requestType.setEndDate(endDate);
        //设置实时数据类型
        //2：账户 10：计划 11：单元 14：关键词(keywordid) 12：创意 15：配对 3：地域 9：关键词(wordid)
        requestType.setReportType(11);
        // 设置搜索推广渠道
        // 0：全部搜索推广设备  1：仅计算机 2：仅移动
        requestType.setDevice(1);

        //创建访问百度接口请求
        GetRealTimeDataRequest dataRequest = new GetRealTimeDataRequest();
        dataRequest.setRealTimeRequestTypes(requestType);
        GetRealTimeDataResponse dataResponse =  reportService.getRealTimeData(dataRequest);
        List<RealTimeResultType> list = dataResponse.getRealTimeResultTypes();
        return list;
    }
    /**
     * 获取单元移动端数据
     * @param _startDate
     * @param _endDate
     * @return
     */
    public static List<RealTimeResultType> getUnitRealTimeDataMobile(String _startDate, String _endDate){
        //初始化时间
        processingTime(_startDate,_endDate);

        //得到设置返回数据工厂
        RealTimeRequestType requestType = new RealTimeRequestType();
        //指定返回数据类型
        requestType.setPerformanceData(Arrays.asList(new String[]{"impression", "click", "ctr", "cost", "cpc", "position", "conversion"}));

        // 指定返回的数据层级
        // 默认为账户
        // 2：账户粒度 3：计划粒度 5：单元粒度 7：创意粒度 11：关键词(keywordid)粒度 12：关键词(keywordid)+创意粒度 6：关键词(wordid)粒度
        requestType.setLevelOfDetails(5);
        //指定起始时间
        requestType.setStartDate(startDate);
        requestType.setEndDate(endDate);
        //设置实时数据类型
        //2：账户 10：计划 11：单元 14：关键词(keywordid) 12：创意 15：配对 3：地域 9：关键词(wordid)
        requestType.setReportType(11);
        // 设置搜索推广渠道
        // 0：全部搜索推广设备  1：仅计算机 2：仅移动
        requestType.setDevice(2);

        //创建访问百度接口请求
        GetRealTimeDataRequest dataRequest = new GetRealTimeDataRequest();
        dataRequest.setRealTimeRequestTypes(requestType);
        GetRealTimeDataResponse dataResponse =  reportService.getRealTimeData(dataRequest);
        List<RealTimeResultType> list = dataResponse.getRealTimeResultTypes();
        return list;
    }
    /**
     * 获取创意PC端数据
     * @param _startDate
     * @param _endDate
     * @return
     */
    public static List<RealTimeResultType> getCreativeRealTimeDataPC(String _startDate, String _endDate){
        //初始化时间
        processingTime(_startDate,_endDate);

        //得到设置返回数据工厂
        RealTimeRequestType requestType = new RealTimeRequestType();
        //指定返回数据类型
        requestType.setPerformanceData(Arrays.asList(new String[]{"impression", "click", "ctr", "cost", "cpc", "position", "conversion"}));

        // 指定返回的数据层级
        // 默认为账户
        // 2：账户粒度 3：计划粒度 5：单元粒度 7：创意粒度 11：关键词(keywordid)粒度 12：关键词(keywordid)+创意粒度 6：关键词(wordid)粒度
        requestType.setLevelOfDetails(7);
        //指定起始时间
        requestType.setStartDate(startDate);
        requestType.setEndDate(endDate);
        //设置实时数据类型
        //2：账户 10：计划 11：单元 14：关键词(keywordid) 12：创意 15：配对 3：地域 9：关键词(wordid)
        requestType.setReportType(12);
        // 设置搜索推广渠道
        // 0：全部搜索推广设备  1：仅计算机 2：仅移动
        requestType.setDevice(1);

        //创建访问百度接口请求
        GetRealTimeDataRequest dataRequest = new GetRealTimeDataRequest();
        dataRequest.setRealTimeRequestTypes(requestType);
        GetRealTimeDataResponse dataResponse =  reportService.getRealTimeData(dataRequest);
        List<RealTimeResultType> list = dataResponse.getRealTimeResultTypes();
        return list;
    }
    /**
     * 获取创意移动端数据
     * @param _startDate
     * @param _endDate
     * @return
     */
    public static List<RealTimeResultType> getCreativeRealTimeDataMobile(String _startDate, String _endDate){
        //初始化时间
        processingTime(_startDate,_endDate);

        //得到设置返回数据工厂
        RealTimeRequestType requestType = new RealTimeRequestType();
        //指定返回数据类型
        requestType.setPerformanceData(Arrays.asList(new String[]{"impression", "click", "ctr", "cost", "cpc", "position", "conversion"}));

        // 指定返回的数据层级
        // 默认为账户
        // 2：账户粒度 3：计划粒度 5：单元粒度 7：创意粒度 11：关键词(keywordid)粒度 12：关键词(keywordid)+创意粒度 6：关键词(wordid)粒度
        requestType.setLevelOfDetails(7);
        //指定起始时间
        requestType.setStartDate(startDate);
        requestType.setEndDate(endDate);
        //设置实时数据类型
        //2：账户 10：计划 11：单元 14：关键词(keywordid) 12：创意 15：配对 3：地域 9：关键词(wordid)
        requestType.setReportType(12);
        // 设置搜索推广渠道
        // 0：全部搜索推广设备  1：仅计算机 2：仅移动
        requestType.setDevice(2);

        //创建访问百度接口请求
        GetRealTimeDataRequest dataRequest = new GetRealTimeDataRequest();
        dataRequest.setRealTimeRequestTypes(requestType);
        GetRealTimeDataResponse dataResponse =  reportService.getRealTimeData(dataRequest);
        List<RealTimeResultType> list = dataResponse.getRealTimeResultTypes();
        return list;
    }
    /**
     * 获取地域PC端数据
     * @param _startDate
     * @param _endDate
     * @return
     */
    public static List<RealTimeResultType> getRegionalRealTimeDataPC(String _startDate, String _endDate){
        //初始化时间
        processingTime(_startDate,_endDate);

        //得到设置返回数据工厂
        RealTimeRequestType requestType = new RealTimeRequestType();
        //指定返回数据类型
        requestType.setPerformanceData(Arrays.asList(new String[]{"impression", "click", "ctr", "cost", "cpc", "position", "conversion"}));

        // 指定返回的数据层级
        // 默认为账户
        // 2：账户粒度 3：计划粒度 5：单元粒度 7：创意粒度 11：关键词(keywordid)粒度 12：关键词(keywordid)+创意粒度 6：关键词(wordid)粒度
        requestType.setLevelOfDetails(5);
        //指定起始时间
        requestType.setStartDate(startDate);
        requestType.setEndDate(endDate);
        //设置实时数据类型
        //2：账户 10：计划 11：单元 14：关键词(keywordid) 12：创意 15：配对 3：地域 9：关键词(wordid)
        requestType.setReportType(3);
        // 设置搜索推广渠道
        // 0：全部搜索推广设备  1：仅计算机 2：仅移动
        requestType.setDevice(1);

        //创建访问百度接口请求
        GetRealTimeDataRequest dataRequest = new GetRealTimeDataRequest();
        dataRequest.setRealTimeRequestTypes(requestType);
        GetRealTimeDataResponse dataResponse =  reportService.getRealTimeData(dataRequest);
        List<RealTimeResultType> list = dataResponse.getRealTimeResultTypes();
        return list;
    }
    /**
     * 获取地域移动端数据
     * @param _startDate
     * @param _endDate
     * @return
     */
    public static List<RealTimeResultType> getRegionalRealTimeDataMobile(String _startDate, String _endDate){
        //初始化时间
        processingTime(_startDate,_endDate);

        //得到设置返回数据工厂
        RealTimeRequestType requestType = new RealTimeRequestType();
        //指定返回数据类型
        requestType.setPerformanceData(Arrays.asList(new String[]{"impression", "click", "ctr", "cost", "cpc", "position", "conversion"}));

        // 指定返回的数据层级
        // 默认为账户
        // 2：账户粒度 3：计划粒度 5：单元粒度 7：创意粒度 11：关键词(keywordid)粒度 12：关键词(keywordid)+创意粒度 6：关键词(wordid)粒度
        requestType.setLevelOfDetails(5);
        //指定起始时间
        requestType.setStartDate(startDate);
        requestType.setEndDate(endDate);
        //设置实时数据类型
        //2：账户 10：计划 11：单元 14：关键词(keywordid) 12：创意 15：配对 3：地域 9：关键词(wordid)
        requestType.setReportType(3);
        // 设置搜索推广渠道
        // 0：全部搜索推广设备  1：仅计算机 2：仅移动
        requestType.setDevice(2);

        //创建访问百度接口请求
        GetRealTimeDataRequest dataRequest = new GetRealTimeDataRequest();
        dataRequest.setRealTimeRequestTypes(requestType);
        GetRealTimeDataResponse dataResponse =  reportService.getRealTimeData(dataRequest);
        List<RealTimeResultType> list = dataResponse.getRealTimeResultTypes();
        return list;
    }

    public static void main(String[] args) {
        List<RealTimeResultType> list = RealTimeDataReports.getAccountRealTimeData("2014-01-25", "2014-01-25");
        List<RealTimeResultType> listPC = RealTimeDataReports.getRegionalRealTimeDataPC("2014-01-25", "2014-01-25");
        int dd = list.size();
        int ddpc = listPC.size();
        System.out.println(list);
    }

}
