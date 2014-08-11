package com.perfect.api.baidu;

import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.mongodb.utils.BaseBaiduService;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by SubDong on 2014/8/7.
 */
public class RealTimeDataReports {

    //得到百度aip
    private CommonService service = null;

    private ReportService reportService = null;

    public RealTimeDataReports(){
        service = BaseBaiduService.getCommonService();
        init();
    }

    public RealTimeDataReports(String userName,String password, String token, String target)
    {
        service = BaseBaiduService.getCommonServiceUser(userName,password,token,target);
        init();
    }
    public void init(){
        try {
            reportService = service.getService(ReportService.class);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化API中需要共用到的属性
     * @param _startDate 开始时间
     * @param _endDate 结束时间
     */
    private Date[] processingTime(String _startDate, String _endDate){
        Date[] date =null;
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
            date = new Date[]{cal.getTime(),cal.getTime()};
        } else {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
            try {
                date = new Date[]{ sd.parse(_startDate),sd.parse(_endDate)};
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    private List<RealTimeResultType> RealTime(List<Long> listKey, int format,int granularity,Date startDate,Date endDate,int dataType,int device,String[] PerformanceData){
        //得到设置返回数据工厂
        RealTimeRequestType requestType = new RealTimeRequestType();
        //指定返回数据类型
        if(PerformanceData != null){
            requestType.setPerformanceData(Arrays.asList(PerformanceData));
        }else{
            if(format == 0){
                requestType.setPerformanceData(Arrays.asList(new String[]{"cost", "cpc", "click", "impression", "ctr", "cpm", "conversion"}));
            }else{
                requestType.setPerformanceData(Arrays.asList(new String[]{"cost", "cpc", "click", "impression", "ctr", "cpm","position", "conversion"}));
            }
        }

        //关键词统计范围下的id集合
        if(listKey != null){
            requestType.setStatIds(listKey);
        }
        // 指定返回的数据层级
        // 默认为账户
        // 2：账户粒度 3：计划粒度 5：单元粒度 7：创意粒度 11：关键词(keywordid)粒度 12：关键词(keywordid)+创意粒度 6：关键词(wordid)粒度
        requestType.setLevelOfDetails(granularity);
        //指定起始时间
        requestType.setStartDate(startDate);
        requestType.setEndDate(endDate);
        //设置实时数据类型
        //2：账户 10：计划 11：单元 14：关键词(keywordid) 12：创意 15：配对 3：地域 9：关键词(wordid)
        requestType.setReportType(dataType);
            // 设置搜索推广渠道
            // 0：全部搜索推广设备  1：仅计算机 2：仅移动
            requestType.setDevice(device);


        //创建访问百度接口请求
        GetRealTimeDataRequest dataRequest = new GetRealTimeDataRequest();
        dataRequest.setRealTimeRequestTypes(requestType);
        GetRealTimeDataResponse dataResponse =  reportService.getRealTimeData(dataRequest);
        List<RealTimeResultType> list = dataResponse.getRealTimeResultTypes();
        return list;
    }

    /**
     * 获取账户数据
     * @param _startDate
     * @param _endDate
     * @return
     */
    public List<RealTimeResultType> getAccountRealTimeData(String _startDate, String _endDate,String[] PerformanceData){
        //初始化时间
        Date[] dates = processingTime(_startDate,_endDate);
        /**
         * RealTime(需要查询ID，返回数据格式，粒度，开始时间，结束时间，实时数据类型,返回数据类型)
         */
        List<RealTimeResultType> resultTypes = RealTime(null,0,2,dates[1],dates[2],2,0,PerformanceData);

        return resultTypes;
    }

    /**
     * 获取关键词PC端数据
     * @param _startDate
     * @param _endDate
     * @return
     */
    public List<RealTimeResultType> getKeyWordidRealTimeDataPC(List<Long> listKey,String[] PerformanceData, String _startDate, String _endDate){
        //初始化时间
        Date[] dates = processingTime(_startDate,_endDate);
        /**
         * RealTime(需要查询ID，返回数据格式，粒度，开始时间，结束时间，实时数据类型)
         */
        List<RealTimeResultType> resultTypes = RealTime(listKey,1,11,dates[1],dates[2],14,1,PerformanceData);

        return resultTypes;
    }

    /**
     * 获取关键词移动端数据
     * @param _startDate
     * @param _endDate
     * @return
     */
    public List<RealTimeResultType> getKeyWordidRealTimeDataMobile(List<Long> listKey,String[] PerformanceData,String _startDate, String _endDate){
        //初始化时间
        Date[] dates = processingTime(_startDate,_endDate);
        /**
         * RealTime(需要查询ID，返回数据格式，粒度，开始时间，结束时间，实时数据类型)
         */
        List<RealTimeResultType> resultTypes = RealTime(listKey,1,11,dates[1],dates[2],14,2,PerformanceData);

        return resultTypes;
    }
    /**
     * 获取单元PC端数据
     * @param _startDate
     * @param _endDate
     * @return
     */
    public List<RealTimeResultType> getUnitRealTimeDataPC(List<Long> listKey,String[] PerformanceData,String _startDate, String _endDate){
        //初始化时间
        Date[] dates = processingTime(_startDate,_endDate);
        /**
         * RealTime(需要查询ID ，返回数据格式，粒度，开始时间，结束时间，实时数据类型)
         */
        List<RealTimeResultType> resultTypes = RealTime(listKey,0,5,dates[1],dates[2],11,1,PerformanceData);

        return resultTypes;
    }
    /**
     * 获取单元移动端数据
     * @param _startDate
     * @param _endDate
     * @return
     */
    public List<RealTimeResultType> getUnitRealTimeDataMobile(List<Long> listKey,String[] PerformanceData,String _startDate, String _endDate){
        //初始化时间
        Date[] dates = processingTime(_startDate,_endDate);
        /**
         * RealTime(需要查询ID ，返回数据格式，粒度，开始时间，结束时间，实时数据类型)
         */
        List<RealTimeResultType> resultTypes = RealTime(listKey,0,5,dates[1],dates[2],11,2,PerformanceData);

        return resultTypes;
    }
    /**
     * 获取创意PC端数据
     * @param _startDate
     * @param _endDate
     * @return
     */
    public List<RealTimeResultType> getCreativeRealTimeDataPC(List<Long> listKey,String[] PerformanceData,String _startDate, String _endDate){
        //初始化时间
        Date[] dates = processingTime(_startDate,_endDate);
        /**
         * RealTime(需要查询ID ，返回数据格式，粒度，开始时间，结束时间，实时数据类型)
         */
        List<RealTimeResultType> resultTypes = RealTime(listKey,1,7,dates[1],dates[2],12,1,PerformanceData);

        return resultTypes;
    }
    /**
     * 获取创意移动端数据
     * @param _startDate
     * @param _endDate
     * @return
     */
    public List<RealTimeResultType> getCreativeRealTimeDataMobile(List<Long> listKey,String[] PerformanceData, String _startDate, String _endDate){
        //初始化时间
        Date[] dates = processingTime(_startDate,_endDate);
        /**
         * RealTime(需要查询ID ，返回数据格式，粒度，开始时间，结束时间，实时数据类型)
         */
        List<RealTimeResultType> resultTypes = RealTime(listKey,1,7,dates[1],dates[2],12,2,PerformanceData);

        return resultTypes;
    }
    /**
     * 获取地域PC端数据
     * @param _startDate
     * @param _endDate
     * @return
     */
    public List<RealTimeResultType> getRegionalRealTimeDataPC(List<Long> listKey,String[] PerformanceData, String _startDate, String _endDate){
        //初始化时间
        Date[] dates = processingTime(_startDate,_endDate);
        /**
         * RealTime(需要查询ID ，返回数据格式，粒度，开始时间，结束时间，实时数据类型)
         */
        List<RealTimeResultType> resultTypes = RealTime(listKey,1,5,dates[1],dates[2],3,1,PerformanceData);

        return resultTypes;
    }
    /**
     * 获取地域移动端数据
     * @param _startDate
     * @param _endDate
     * @return
     */
    public List<RealTimeResultType> getRegionalRealTimeDataMobile(List<Long> listKey,String[] PerformanceData,String _startDate, String _endDate){
        //初始化时间
        Date[] dates = processingTime(_startDate,_endDate);
        /**
         * RealTime(需要查询ID ，返回数据格式，粒度，开始时间，结束时间，实时数据类型)
         */
        List<RealTimeResultType> resultTypes = RealTime(listKey,1,5,dates[1],dates[2],3,2,PerformanceData);

        return resultTypes;
    }
}
