package com.perfect.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.dao.AccountManageDAO;
import com.perfect.entity.AccountReportEntity;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.mongodb.utils.DateUtils;
import com.perfect.service.AccountManageService;
import com.perfect.utils.JSONUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by baizz on 2014-8-21.
 */
@Service("accountManageService")
public class AccountManageServiceImpl implements AccountManageService {

    @Resource
    private AccountManageDAO<BaiduAccountInfoEntity> accountManageDAO;

    public Map<String, Object> getAccountTree() {
        ArrayNode treeNodes = accountManageDAO.getAccountTree();
        Map<String, Object> trees = new HashMap<>();
        trees.put("trees", treeNodes);
        return trees;
    }

    @Override
    public Map<String, Object> getAllBaiduAccount(String currSystemUserName) {
        List<BaiduAccountInfoEntity> list = accountManageDAO.getBaiduAccountItems(currSystemUserName);
        return JSONUtils.getJsonMapData(list);
    }

    public Map<String, Object> getBaiduAccountInfoByUserId(Long baiduUserId) {
        BaiduAccountInfoEntity entity = accountManageDAO.findByBaiduUserId(baiduUserId);
        Map<String, Object> results = JSONUtils.getJsonMapData(entity);
        results.put("cost", getYesterdayCost(baiduUserId));
        results.put("costRate", accountManageDAO.getCostRate());
        //从凤巢获取budgetOfflineTime
        try {
            CommonService service = ServiceFactory.getInstance(entity.getBaiduUserName(), entity.getBaiduPassword(), entity.getToken(), null);
            AccountService accountService = service.getService(AccountService.class);
            GetAccountInfoRequest request = new GetAccountInfoRequest();
            GetAccountInfoResponse response = accountService.getAccountInfo(request);
            AccountInfoType accountInfo = response.getAccountInfoType();
            List<OfflineTimeType> list = accountInfo.getBudgetOfflineTime();
            if (list.size() == 0) {
                results.put("budgetOfflineTime", "");
            } else {
                results.put("budgetOfflineTime", getJson(list));
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return results;
    }

    public BaiduAccountInfoEntity getBaiduAccountInfoById(Long baiduUserId) {
        return accountManageDAO.findByBaiduUserId(baiduUserId);
    }

    public void updateBaiduAccount(BaiduAccountInfoEntity entity) {
        accountManageDAO.updateBaiduAccountInfo(entity);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getAccountReports(int number) {
        List<Date> dates = (List<Date>) DateUtils.getsLatestAnyDays("MM-dd", number).get(DateUtils.KEY_DATE);
        List<AccountReportEntity> list = accountManageDAO.getAccountReports(dates);
        Map<String, Object> values = JSONUtils.getJsonMapData(list);
        values.put("dates", JSONUtils.getJsonObjectArray(DateUtils.getsLatestAnyDays("MM-dd", 7).get(DateUtils.KEY_STRING)));
        return values;
    }

    public Double getYesterdayCost(Long accountId) {
        return accountManageDAO.getYesterdayCost(accountId);
    }

    protected JsonNode getJson(Object o) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd/HH"));
        Map<String, Object> values = new LinkedHashMap<>();
        try {
            JsonNode jsonNode = mapper.readTree(mapper.writeValueAsBytes(o));
            return jsonNode;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}