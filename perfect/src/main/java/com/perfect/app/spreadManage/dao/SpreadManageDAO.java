package com.perfect.app.spreadManage.dao;

import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.core.ResHeaderUtil;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.utils.BaiduServiceSupport;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by baizz on 2014-6-4.
 */
@Repository(value = "spreadManageDAO")
public class SpreadManageDAO {
    private static CommonService service = BaiduServiceSupport.getCommonService();

    //生成账户树
    public JSONArray getAccountTree() {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject;
        //推广计划树
        CampaignType[] campaignTypes = getAllCampaignType();
        List<Long> campaignIds = new ArrayList<>();
        for (CampaignType campaignType : campaignTypes) {
            campaignIds.add(campaignType.getCampaignId());
            jsonObject = new JSONObject();
            jsonObject.put("id", campaignType.getCampaignId());
            jsonObject.put("pId", 0);
            jsonObject.put("name", campaignType.getCampaignName());
            jsonArray.add(jsonObject);
            jsonObject = null;
        }
        //推广单元树
        try {
            AdgroupService adgroupService = service.getService(AdgroupService.class);
            GetAdgroupByCampaignIdRequest getAdgroupByCampaignIdRequest = new GetAdgroupByCampaignIdRequest();
            getAdgroupByCampaignIdRequest.setCampaignIds(campaignIds);
            GetAdgroupByCampaignIdResponse getAdgroupByCampaignIdResponse =
                    adgroupService.getAdgroupByCampaignId(getAdgroupByCampaignIdRequest);
            List<CampaignAdgroup> campaignAdgroups =
                    getAdgroupByCampaignIdResponse.getCampaignAdgroups();
            for (CampaignAdgroup campaignAdgroup : campaignAdgroups) {
                Long campaignId = campaignAdgroup.getCampaignId();
                List<AdgroupType> adgroupTypes = campaignAdgroup.getAdgroupTypes();
                for (AdgroupType adgroupType : adgroupTypes) {
                    jsonObject = new JSONObject();
                    jsonObject.put("id", adgroupType.getAdgroupId());
                    jsonObject.put("pId", campaignId);
                    jsonObject.put("name", adgroupType.getAdgroupName());
                    jsonArray.add(jsonObject);
                    jsonObject = null;
                }
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    //实时报告
    public RealTimeResultType[] getRealTimeResultType(String _startDate, String _endDate, int levelOfDetails,
                                                      int reportType, int statRange, int number, String... id) {
        RealTimeResultType[] realTimeResultTypes = null;
        try {
            Date startDate;
            Date endDate;
            if (_startDate.length() == 0 || _endDate.length() == 0) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                cal.set(Calendar.DAY_OF_MONTH, -1);
                startDate = cal.getTime();
                endDate = cal.getTime();
            } else {
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-M-d");
                startDate = sd.parse(_startDate);
                endDate = sd.parse(_endDate);
            }

            int category = -1;
            Long _id = 0l;
            if (id != null && id.length == 1) {
                String[] params = id[0].split(",");
                _id = Long.valueOf(params[0]);
                category = Integer.valueOf(params[1]);
            }

            List<Long> ids = new ArrayList<>();
            if (category == -1)
                //默认是账户
                ids = getAllCampaignIdOfAccount();
            else if (category == 0)
                //计划
                ids = getAllAdgroupIdByCampaignId(_id);
            else if (category == 1)
                //单元
                ids = getAllKeywordIdByAdgroupId(_id);

            ReportService reportService = service.getService(ReportService.class);

            RealTimeRequestType realTimeRequestType = new RealTimeRequestType();

            realTimeRequestType.setStatIds(ids);
            //参数设置
            //指定返回数据类型
            realTimeRequestType.setPerformanceData(Arrays.asList(new String[]{"cost", "cpc", "click", "impression"}));//, "ctr", "cpm", "position", "conversion"
            realTimeRequestType.setStartDate(startDate);
            realTimeRequestType.setEndDate(endDate);
            //指定返回的数据层级
            realTimeRequestType.setLevelOfDetails(levelOfDetails);
            //指定实时数据类型
            realTimeRequestType.setReportType(reportType);
            //指定返回数据条数
            realTimeRequestType.setNumber(number);
            realTimeRequestType.setStatRange(statRange);
            GetRealTimeDataRequest getRealTimeDataRequest = new GetRealTimeDataRequest();
            getRealTimeDataRequest.setRealTimeRequestTypes(realTimeRequestType);

            /*
            mongoTemplate.insert(getRealTimeDataRequest, "GetRealTimeDataRequest");
            List<GetRealTimeDataRequest> requestList = mongoTemplate.find(
                    new Query().addCriteria(new Criteria().all()),
                    GetRealTimeDataRequest.class,
                    "GetRealTimeDataRequest");
            GetRealTimeDataRequest request = requestList.get(requestList.size() - 1);
            */
            GetRealTimeDataResponse response = reportService.getRealTimeData(getRealTimeDataRequest);
            List<RealTimeResultType> list = response.getRealTimeResultTypes();
            realTimeResultTypes = list.toArray(new RealTimeResultType[list.size()]);
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return realTimeResultTypes;
    }

    //获取账户下的所有推广计划ID
    private List<Long> getAllCampaignIdOfAccount() {
        CampaignService campaignService = null;
        try {
            campaignService = service.getService(CampaignService.class);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        GetAllCampaignIdRequest request = new GetAllCampaignIdRequest();
        GetAllCampaignIdResponse response = campaignService.getAllCampaignId(request);
        List<Long> campaignIds = response.getCampaignIds();
        return campaignIds;
    }

    //根据推广计划ID获取其下属的所有推广单元ID
    private List<Long> getAllAdgroupIdByCampaignId(Long campaignId) {
        AdgroupService adgroupService = null;
        try {
            adgroupService = service.getService(AdgroupService.class);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        GetAdgroupIdByCampaignIdRequest request = new GetAdgroupIdByCampaignIdRequest();
        request.setCampaignIds(Arrays.asList(new Long[]{campaignId}));
        GetAdgroupIdByCampaignIdResponse response = adgroupService.getAdgroupIdByCampaignId(request);
        List<Long> adgroupIds = response.getCampaignAdgroupId(0).getAdgroupIds();

        return adgroupIds;
    }

    //根据推广计划ID获取其下属的所有推广单元ID和名称
    public AdgroupInfo[] getAdgroupByCampaignId(Long campaignId) {
        AdgroupService adgroupService = null;
        AdgroupInfo[] adgroupInfos = null;
        try {
            adgroupService = service.getService(AdgroupService.class);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        GetAdgroupByCampaignIdRequest request = new GetAdgroupByCampaignIdRequest();
        request.setCampaignIds(Arrays.asList(new Long[]{campaignId}));
        GetAdgroupByCampaignIdResponse response = adgroupService.getAdgroupByCampaignId(request);
        List<AdgroupType> list = response.getCampaignAdgroup(0).getAdgroupTypes();
        List<AdgroupInfo> list1 = new ArrayList<>();
        for (AdgroupType adgroupType : list) {
            AdgroupInfo entity = new AdgroupInfo();
            entity.setAdgroupId(adgroupType.getAdgroupId());
            entity.setAdgroupName(adgroupType.getAdgroupName());
            entity.setCampaignId(adgroupType.getCampaignId());
            entity.setCampaignName("");
            list1.add(entity);
            entity = null;
        }
        adgroupInfos = list1.toArray(new AdgroupInfo[list1.size()]);
        return adgroupInfos;
    }

    //根据推广单元ID获取其下属的所有关键词ID
    private List<Long> getAllKeywordIdByAdgroupId(Long adgroupId) {
        KeywordService keywordService = null;
        try {
            keywordService = service.getService(KeywordService.class);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        GetKeywordIdByAdgroupIdRequest request = new GetKeywordIdByAdgroupIdRequest();
        request.setAdgroupIds(Arrays.asList(new Long[]{adgroupId}));
        GetKeywordIdByAdgroupIdResponse response = keywordService.getKeywordIdByAdgroupId(request);
        List<Long> keywordIds = response.getGroupKeywordId(0).getKeywordIds();
        return keywordIds;
    }

    //获取账户下的所有推广计划
    private CampaignType[] getAllCampaignType() {
        CampaignType[] campaignTypes = null;
        try {
            CampaignService campaignService = service.getService(CampaignService.class);
            GetAllCampaignRequest request = new GetAllCampaignRequest();
            GetAllCampaignResponse response = campaignService.getAllCampaign(request);
            List<CampaignType> list = response.getCampaignTypes();
            campaignTypes = list.toArray(new CampaignType[list.size()]);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return campaignTypes;
    }

    //输入关键词, 获取推荐词
    public KRResult[] getKRResult(String aSeedWord) {
        KRService krService = null;
        KRResult[] _krResult = null;
        Set<String> set = new HashSet<>();
        try {
            krService = service.getService(KRService.class);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        GetKRbySeedWordRequest getKRbySeedWordRequest = new GetKRbySeedWordRequest();
        getKRbySeedWordRequest.setSeedWord(aSeedWord);
        SeedFilter filter = new SeedFilter();
        //广泛匹配模式
        filter.setMatchType(3);
        getKRbySeedWordRequest.setSeedFilter(filter);
        GetKRbySeedWordResponse getKRbySeedWordResponse = krService.getKRbySeedWord(getKRbySeedWordRequest);
        List<KRResult> krResult = getKRbySeedWordResponse.getKrResult();
        _krResult = krResult.toArray(new KRResult[krResult.size()]);
        return _krResult;
    }

    //自动分组
    public AutoAdGroupResult[] autoGroup(String[] seedWords, Long campaignId, Long adgroupId) {
        KRService krService = null;
        AutoAdGroupResult[] autoAdGroupResults = null;

        Long _campaignId = campaignId;
        String campaignName;

        /*if (campaignId == null) {
            CampaignType newCampaign = addCampaign();
            _campaignId = newCampaign.getCampaignId();
            campaignName = newCampaign.getCampaignName();
        } else
            campaignName = getCampaignNameByCampaignId(campaignId);*/

        try {
            krService = service.getService(KRService.class);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        GetAdGroupBySeedWordsRequest request = new GetAdGroupBySeedWordsRequest();
        request.setSeedWords(Arrays.asList(seedWords));
        request.setAdGroupIds(null);
        GetAdGroupBySeedWordsResponse response = krService.getAdGroupBySeedWords(request);
        List<AutoAdGroupResult> list = response.getAutoAdGroupResults();
        autoAdGroupResults = list.toArray(new AutoAdGroupResult[list.size()]);
        return autoAdGroupResults;
    }

    //添加关键词时没有指定推广计划, 则新建计划并返回新建计划的ID
    public Long getNewCampaign(String campaignName) {
        CampaignType newCampaign = new CampaignType();
        CampaignService campaignService = null;
        try {
            campaignService = service.getService(CampaignService.class);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        String _campaignName = campaignName;
        if (StringUtils.isBlank(campaignName))
            _campaignName = "新建计划";

        newCampaign.setCampaignName(_campaignName);
        AddCampaignRequest request = new AddCampaignRequest();
        request.setCampaignTypes(Arrays.asList(new CampaignType[]{newCampaign}));
        AddCampaignResponse response = campaignService.addCampaign(request);
        Long newCampaignId = response.getCampaignType(0).getCampaignId();
        return newCampaignId;
    }

    //根据推广计划ID获取推广计划名称
    private String getCampaignNameByCampaignId(Long campaignId) {
        CampaignService campaignService = null;
        try {
            campaignService = service.getService(CampaignService.class);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        GetCampaignByCampaignIdRequest request = new GetCampaignByCampaignIdRequest();
        request.setCampaignIds(Arrays.asList(new Long[]{campaignId}));
        GetCampaignByCampaignIdResponse response = campaignService.getCampaignByCampaignId(request);
        ResHeaderUtil.getJsonResHeader(true);
        String campaignName = response.getCampaignType(0).getCampaignName();
        return campaignName;
    }

    //添加关键词时没有指定推广单元, 则新建单元并返回新建单元的ID
    public Long[] getNewAdgroupId(String[] adgroupNames, Long campaignId) {
        AdgroupService adgroupService = null;
        Long[] newAdgroupIds = null;
        try {
            adgroupService = service.getService(AdgroupService.class);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        AddAdgroupRequest request = new AddAdgroupRequest();
        List<AdgroupType> list = new ArrayList<>();
        AdgroupType adgroupType = null;
        for (int i = 0, l = adgroupNames.length; i < l; i++) {
            adgroupType = new AdgroupType();
            adgroupType.setCampaignId(campaignId);
            adgroupType.setAdgroupName(adgroupNames[i]);
            adgroupType.setMaxPrice(0.5);
            list.add(adgroupType);
        }
        request.setAdgroupTypes(list);
        AddAdgroupResponse response = adgroupService.addAdgroup(request);
        List<AdgroupType> list1 = response.getAdgroupTypes();
        List<Long> list2 = new ArrayList<>();
        for (AdgroupType entity : list1)
            list2.add(entity.getAdgroupId());
        newAdgroupIds = list2.toArray(new Long[list2.size()]);
        return newAdgroupIds;
    }

    //添加关键词
    public void addKeywords(List<KeywordType> keywordTypes) {
        KeywordService keywordService = null;
        try {
            keywordService = service.getService(KeywordService.class);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        AddKeywordRequest request = new AddKeywordRequest();
        request.setKeywordTypes(keywordTypes);
        AddKeywordResponse response = keywordService.addKeyword(request);
    }

}
