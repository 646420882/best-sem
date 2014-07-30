package com.perfect.app.accountCenter.dao.impl;

import com.perfect.api.baidu.AccountRealTimeData;
import com.perfect.api.baidu.KeywordRealTimeData;
import com.perfect.app.accountCenter.dao.InitializeAccountDAO;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.core.AppContext;
import com.perfect.entity.AccountRealTimeDataVOEntity;
import com.perfect.entity.KeywordRealTimeDataVOEntity;
import com.perfect.mongodb.utils.BaseMongoTemplate;
import com.perfect.utils.BaiduServiceSupport;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baizz on 2014-7-23.
 */
public class InitializeAccountDAOImpl implements InitializeAccountDAO {

    private String currUserName = AppContext.getUser().toString();

    private CommonService commonService = BaiduServiceSupport.getCommonService();

    private List<Long> list = new ArrayList<>();

    private Long baiduAccountUserId;

    {
        AccountService accountService = null;
        KeywordService keywordService = null;
        AdgroupService adgroupService = null;
        try {
            accountService = commonService.getService(AccountService.class);
            keywordService = commonService.getService(KeywordService.class);
            adgroupService = commonService.getService(AdgroupService.class);
        } catch (ApiException e) {
            e.printStackTrace();
        }

        //获取账户ID
        GetAccountInfoRequest getAccountInfoRequest = new GetAccountInfoRequest();
        GetAccountInfoResponse getAccountInfoResponse = accountService.getAccountInfo(getAccountInfoRequest);
        baiduAccountUserId = getAccountInfoResponse.getAccountInfoType().getUserid();

        GetAllAdgroupIdRequest getAllAdgroupIdRequest = new GetAllAdgroupIdRequest();
        GetAllAdgroupIdResponse getAllAdgroupIdResponse = adgroupService.getAllAdgroupId(getAllAdgroupIdRequest);
        List<Long> allAdgroupId = new ArrayList<>();
        for (CampaignAdgroupId entity : getAllAdgroupIdResponse.getCampaignAdgroupIds()) {
            allAdgroupId.addAll(entity.getAdgroupIds());
        }

        GetKeywordIdByAdgroupIdRequest getKeywordIdByAdgroupIdRequest = new GetKeywordIdByAdgroupIdRequest();
        getKeywordIdByAdgroupIdRequest.setAdgroupIds(allAdgroupId);
        GetKeywordIdByAdgroupIdResponse getKeywordIdByAdgroupIdResponse = keywordService.getKeywordIdByAdgroupId(getKeywordIdByAdgroupIdRequest);
        for (GroupKeywordId entity : getKeywordIdByAdgroupIdResponse.getGroupKeywordIds()) {
            list.addAll(entity.getKeywordIds());
        }
    }

    public void getBeforeKeywordRealTimeType() {
        List<RealTimeResultType> list1 = KeywordRealTimeData.getKeywordRealTimeData(list, "2014-01-25", "2014-02-15");
        if (list1.size() == 0)
            return;

        //数据存储处理
        MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate("perfect");
        List<KeywordRealTimeDataVOEntity> listVO = new ArrayList<>();

        String logDate = list1.get(0).getDate();

        currUserName = "perfect";
        currUserName = currUserName.substring(0, 1).toUpperCase() + currUserName.substring(1, currUserName.length());
        //创建collection
        String collectionName = currUserName + "-KeywordRealTimeData-log-" + logDate;
        for (RealTimeResultType entity : list1) {
            //创建日志内容
            KeywordRealTimeDataVOEntity vo = new KeywordRealTimeDataVOEntity();
            vo.setKeywordId(entity.getID());
            vo.setKeywordName(entity.getName().get(3));
            vo.setImpression(Integer.valueOf(entity.getKPI(0)));
            vo.setClick(Integer.valueOf(entity.getKPI(1)));
            vo.setCtr(Double.valueOf(entity.getKPI(2)));
            vo.setCost(Double.valueOf(entity.getKPI(3)));
            vo.setCpc(Double.valueOf(entity.getKPI(4)));
            vo.setPosition(Double.valueOf(entity.getKPI(5)));
            vo.setConversion(Double.valueOf(entity.getKPI(6)));
            //日期
            if (!logDate.equals(entity.getDate())) {
                mongoTemplate.insert(listVO, collectionName);
                logDate = entity.getDate();
                collectionName = currUserName + "-KeywordRealTimeData-log-" + logDate;
                listVO.clear();
                continue;
            }
            listVO.add(vo);
        }

    }

    public void getBeforeAccountRealTimeType() {
        List<RealTimeResultType> list1 = AccountRealTimeData.getAccountRealTimeData("2014-01-25", "2014-02-25");
        if (list1.size() == 0)
            return;

        //数据存储处理
        MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate("perfect");
        List<AccountRealTimeDataVOEntity> listVO = new ArrayList<>();

        for (RealTimeResultType entity : list1) {
            //创建日志内容
            AccountRealTimeDataVOEntity vo = new AccountRealTimeDataVOEntity();
            vo.setAccountId(entity.getID());
            vo.setAccountName(entity.getName().get(0));
            vo.setDate(entity.getDate());
            vo.setImpression(Integer.valueOf(entity.getKPI(0)));
            vo.setClick(Integer.valueOf(entity.getKPI(1)));
            vo.setCtr(Double.valueOf(entity.getKPI(2)));
            vo.setCost(Double.valueOf(entity.getKPI(3)));
            vo.setCpc(Double.valueOf(entity.getKPI(4)));
            vo.setConversion(Double.valueOf(entity.getKPI(5)));
            listVO.add(vo);
        }
        mongoTemplate.insert(listVO, AccountRealTimeDataVOEntity.class);
    }

    public static void main(String[] args) {
        new InitializeAccountDAOImpl().getBeforeAccountRealTimeType();
    }

}
