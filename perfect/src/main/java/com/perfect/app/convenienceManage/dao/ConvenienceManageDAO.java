package com.perfect.app.convenienceManage.dao;

import com.perfect.app.convenienceManage.vo.AttentionReport;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.mongodb.utils.AbstractBaseMongoTemplate;
import com.perfect.utils.BaiduServiceSupport;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by baizz on 2014-6-12.
 */
@Repository("convenienceManageDAO")
public class ConvenienceManageDAO extends AbstractBaseMongoTemplate {
    private static CommonService service = BaiduServiceSupport.getService();

    /*
     * 根据用户提供的待搜索关键字, 返回当前用户账户中的相关关键词的搜索结果.
     * 用户不限定结果搜索位置时, 默认返回前100搜索结果.
     */
    public KeywordInfo[] getMatchKeywordInfoFromAccount(String searchWord) {
        KeywordInfo[] keywordInfos = null;
        try {
            SearchService searchService = service.getService(SearchService.class);
            GetKeywordBySearchRequest getKeywordBySearchRequest = new GetKeywordBySearchRequest();
            //设置待搜索关键字
            getKeywordBySearchRequest.setSearchWord(searchWord);
            //限定返回部分搜索结果的起始位置, 默认为1
            getKeywordBySearchRequest.setStartNum(1);
            //限定返回部分搜索结果的终止位置, 默认为100
            getKeywordBySearchRequest.setEndNum(100);
            //搜索关键词匹配类型
            getKeywordBySearchRequest.setSearchType(0);//0, 模糊匹配; 1, 精确匹配
            GetKeywordBySearchResponse getKeywordBySearchResponse =
                    searchService.getKeywordBySearch(getKeywordBySearchRequest);
            List<KeywordInfo> keywordInfoList = getKeywordBySearchResponse.getKeywordInfos();
            keywordInfos = keywordInfoList.toArray(new KeywordInfo[keywordInfoList.size()]);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return keywordInfos;
    }

    //添加新关注
    public void addAttention(KeywordInfo[] keywordInfos) {
        for (KeywordInfo keywordInfo : keywordInfos)
            _mongoTemplate.save(keywordInfo, "KeywordInfo");
    }

    //获取关键词监控报告
    public AttentionReport[] getKeywordReport() {
        AttentionReport[] attentionReports = null;
        try {
            //昨日
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            cal.set(Calendar.DAY_OF_MONTH, -1);
            Date date = cal.getTime();
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-M-d");
            //测试时用该日期
            Date _date = sd.parse("2014-02-05");
            ReportService reportService = service.getService(ReportService.class);
            //实时报告请求
            RealTimeRequestType realTimeRequestType = new RealTimeRequestType();
            //参数设置
            //指定返回数据
            realTimeRequestType.setPerformanceData(Arrays.asList(new String[]{"cost", "click", "impression", "position"}));
            realTimeRequestType.setStartDate(_date);
            realTimeRequestType.setEndDate(_date);
            //指定返回的数据层级
            realTimeRequestType.setLevelOfDetails(11);
            //指定实时数据类型
            realTimeRequestType.setReportType(14);
            //指定返回数据条数
            realTimeRequestType.setNumber(1000);
            //指定统计范围, 为11时表示关键词(keywordid)范围
            realTimeRequestType.setStatRange(11);
            //指定统计时间单位, 默认为5, 日报
            realTimeRequestType.setUnitOfTime(5);
            realTimeRequestType.setStatIds(getAttentionKeywordId());

            GetRealTimeDataRequest getRealTimeDataRequest = new GetRealTimeDataRequest();
            getRealTimeDataRequest.setRealTimeRequestTypes(realTimeRequestType);
            GetRealTimeDataResponse getRealTimeDataResponse = reportService.getRealTimeData(getRealTimeDataRequest);
            List<RealTimeResultType> list =
                    getRealTimeDataResponse.getRealTimeResultTypes();
            AttentionReport entity = null;
            KeywordService keywordService = service.getService(KeywordService.class);
            GetKeywordByKeywordIdRequest getKeywordByKeywordIdRequest;
            GetKeywordByKeywordIdResponse getKeywordByKeywordIdResponse;
            GetKeyword10QualityRequest getKeyword10QualityRequest;
            GetKeyword10QualityResponse getKeyword10QualityResponse;
            List<AttentionReport> attentionReportList = new ArrayList<>();
            for (RealTimeResultType result : list) {
                entity = new AttentionReport();
                entity.setKeywordId(result.getID());
                entity.setKeyword(result.getName(3));
                entity.setRank(result.getKPI(3));

                //当前出价
                getKeywordByKeywordIdRequest = new GetKeywordByKeywordIdRequest();
                getKeywordByKeywordIdRequest.setKeywordIds(Arrays.asList(new Long[]{result.getID()}));
                getKeywordByKeywordIdResponse =
                        keywordService.getKeywordByKeywordId(getKeywordByKeywordIdRequest);
                entity.setCurrentPrice(getKeywordByKeywordIdResponse.getKeywordType(0).getPrice());
                entity.setClick(result.getKPI(1));
                entity.setCost(result.getKPI(0));

                //质量度
                getKeyword10QualityRequest = new GetKeyword10QualityRequest();
                getKeyword10QualityRequest.setIds(Arrays.asList(new Long[]{result.getID()}));
                getKeyword10QualityRequest.setType(11);
                getKeyword10QualityRequest.setDevice(0);
                getKeyword10QualityResponse =
                        keywordService.getKeyword10Quality(getKeyword10QualityRequest);
                entity.setQualityGrade(getKeyword10QualityResponse.getKeyword10Quality(0).getPcQuality().toString());
                attentionReportList.add(entity);
                getKeywordByKeywordIdRequest = null;
                getKeyword10QualityRequest = null;
            }
            attentionReports = attentionReportList.toArray(new AttentionReport[attentionReportList.size()]);
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return attentionReports;
    }

    //手动修改关键词价格
    public void modifyKeywordPrice(String keywordId, String keywordPrice) {
        try {
            KeywordService keywordService = service.getService(KeywordService.class);
            UpdateKeywordRequest updateKeywordRequest = new UpdateKeywordRequest();
            List<KeywordType> list = new ArrayList<>();
            KeywordType keywordType = new KeywordType();
            keywordType.setKeywordId(Long.valueOf(keywordId));
            keywordType.setPrice(Double.valueOf(keywordPrice));
            list.add(keywordType);
            updateKeywordRequest.setKeywordTypes(list);
            UpdateKeywordResponse updateKeywordResponse =
                    keywordService.updateKeyword(updateKeywordRequest);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    //将关键词价格恢复为单元出价
    public void revertToUnitPrice(String keywordId) {
        try {
            KeywordService keywordService = service.getService(KeywordService.class);
            UpdateKeywordRequest updateKeywordRequest = new UpdateKeywordRequest();
            List<KeywordType> list = new ArrayList<>();
            KeywordType keywordType = new KeywordType();
            keywordType.setKeywordId(Long.valueOf(keywordId));
            keywordType.setPrice(getAdgroupMaxPriceByKeywordId(Long.valueOf(keywordId)));
            list.add(keywordType);
            updateKeywordRequest.setKeywordTypes(list);
            UpdateKeywordResponse updateKeywordResponse =
                    keywordService.updateKeyword(updateKeywordRequest);
        } catch (ApiException e) {
            e.printStackTrace();
        }

    }

    //取消关注
    public void cancelAttention(String[] keywordIds) {
        for (String id : keywordIds)
            _mongoTemplate.remove(
                    new Query(Criteria.where("keywordId").is(Long.valueOf(id))),
                    KeywordInfo.class,
                    "KeywordInfo"
            );
    }

    //获取所有监控关键词的ID
    private List<Long> getAttentionKeywordId() {
        List<Long> ids = new ArrayList<>();
        for (KeywordInfo keywordInfo : _mongoTemplate.find(
                new Query(new Criteria().all()),
                KeywordInfo.class,
                "KeywordInfo"))
            ids.add(keywordInfo.getKeywordId());
        return ids;
    }

    //根据关键词ID获取其所在推广单元的出价
    private Double getAdgroupMaxPriceByKeywordId(Long keywordId) {
        Double maxPrice = 0.;
        try {
            AdgroupService adgroupService = service.getService(AdgroupService.class);
            KeywordService keywordService = service.getService(KeywordService.class);
            GetKeywordByKeywordIdRequest getKeywordByKeywordIdRequest = new GetKeywordByKeywordIdRequest();
            getKeywordByKeywordIdRequest.setKeywordIds(Arrays.asList(new Long[]{Long.valueOf(keywordId)}));
            GetKeywordByKeywordIdResponse getKeywordByKeywordIdResponse =
                    keywordService.getKeywordByKeywordId(getKeywordByKeywordIdRequest);
            //由关键词ID得到其所在推广单元信息
            GetAdgroupByAdgroupIdRequest getAdgroupByAdgroupIdRequest = new GetAdgroupByAdgroupIdRequest();
            getAdgroupByAdgroupIdRequest.setAdgroupIds(Arrays.asList(new Long[]{getKeywordByKeywordIdResponse.getKeywordType(0).getAdgroupId()}));
            GetAdgroupByAdgroupIdResponse getAdgroupByAdgroupIdResponse = adgroupService.getAdgroupByAdgroupId(getAdgroupByAdgroupIdRequest);
            maxPrice = getAdgroupByAdgroupIdResponse.getAdgroupType(0).getMaxPrice();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return maxPrice;
    }
}
