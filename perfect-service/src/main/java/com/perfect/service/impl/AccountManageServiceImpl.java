package com.perfect.service.impl;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.perfect.dao.AccountManageDAO;
import com.perfect.entity.AccountReportEntity;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.mongodb.utils.DateUtils;
import com.perfect.service.AccountManageService;
import com.perfect.utils.JSONUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-8-21.
 */
@Service("accountManageService")
public class AccountManageServiceImpl implements AccountManageService<BaiduAccountInfoEntity> {

    @Resource
    private AccountManageDAO<BaiduAccountInfoEntity> accountManageDAO;

    public Map<String, Object> getAccountTree(BaiduAccountInfoEntity o) {
        ArrayNode treeNodes = accountManageDAO.getAccountTree(o);
        Map<String, Object> trees = new HashMap<>();
        trees.put("trees", treeNodes);
        return trees;
    }

    public Map<String, Object> getBaiduAccountInfoByUserId(Long baiduUserId) {
        //
        baiduUserId = 2565730l;
        //
        BaiduAccountInfoEntity entity = accountManageDAO.findByBaiduUserId(baiduUserId);
        Map<String, Object> results = JSONUtils.getJsonMapData(entity);
        results.put("cost", accountManageDAO.getYesterdayCost());
        return results;
    }

    public void updateBaiduAccount(BaiduAccountInfoEntity entity) {
        accountManageDAO.updateBaiduAccountInfo(entity);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getAccountReports(int number) {
        List<Date> dates = (List<Date>) DateUtils.getsLatestAnyDays(number).get("_date");
        List<AccountReportEntity> list = accountManageDAO.getAccountReports(dates);
        Map<String, Object> values = JSONUtils.getJsonMapData(list);
        values.put("dates", JSONUtils.getJsonObjectArray(DateUtils.getsLatestAnyDays(7).get("_string")));
        return values;
    }
}