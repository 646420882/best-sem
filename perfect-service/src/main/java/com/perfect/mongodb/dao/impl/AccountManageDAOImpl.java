package com.perfect.mongodb.dao.impl;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.AccountInfoType;
import com.perfect.autosdk.sms.v3.AccountService;
import com.perfect.autosdk.sms.v3.GetAccountInfoRequest;
import com.perfect.autosdk.sms.v3.GetAccountInfoResponse;
import com.perfect.core.AppContext;
import com.perfect.dao.AccountManageDAO;
import com.perfect.dao.SystemUserDAO;
import com.perfect.entity.BaiduAccountInfoEntity;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by baizz on 2014-6-25.
 */
@Repository(value = "accountManageDAO")
public class AccountManageDAOImpl implements AccountManageDAO<BaiduAccountInfoEntity> {

    private String currUserName = AppContext.getUser();

    @Resource(name = "systemUserDAO")
    private SystemUserDAO systemUserDAO;

    /**
     * 百度账户树
     *
     * @param list
     * @return
     */
    @Override
    public ArrayNode getAccountTree(List<BaiduAccountInfoEntity> list) {
        /*JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject;
        for (BaiduAccountInfoEntity account : list) {
            jsonObject = new JSONObject();
            jsonObject.put("id", account.getId());
            jsonObject.put("pId", 0);
            jsonObject.put("name", account.getBaiduUserName());
            jsonArray.add(jsonObject);
            jsonObject = null;
        }*/
        return null;
    }

    /**
     * ---
     *
     * @param currUserName
     * @return
     */
    @Override
    public List<BaiduAccountInfoEntity> getBaiduAccountItems(String currUserName) {
        List<BaiduAccountInfoEntity> list = systemUserDAO
                .findByUserName(currUserName)
                .getBaiduAccountInfoEntities();
        return list;
    }

    /**
     * ---
     *
     * @param baiduUserId
     * @return
     */
    @Override
    public BaiduAccountInfoEntity findByBaiduUserId(Long baiduUserId) {
        List<BaiduAccountInfoEntity> list = getBaiduAccountItems(currUserName);
        BaiduAccountInfoEntity baiduAccount = new BaiduAccountInfoEntity();
        for (BaiduAccountInfoEntity entity : list) {
            if (baiduUserId.equals(entity.getId())) {
                baiduAccount.setId(baiduUserId);
                baiduAccount.setBaiduUserName(entity.getBaiduUserName());
                baiduAccount.setBaiduPassword(entity.getBaiduPassword());
                baiduAccount.setToken(entity.getToken());
                break;
            }
        }
        return baiduAccount;
    }

    @Override
    public void updateAccountData(Long baiduUserId) {
    }

    /**
     * @param username
     * @param password
     * @param token
     * @return
     */
    @Override
    public List<BaiduAccountInfoEntity> getBaiduAccountInfos(String username, String password, String token) {
        List<BaiduAccountInfoEntity> list = new ArrayList<>();
        Long id = getBaiduAccountId(username, password, token);
        BaiduAccountInfoEntity entity = new BaiduAccountInfoEntity();
        entity.setId(id);
        entity.setBaiduUserName(username);
        entity.setBaiduPassword(password);
        entity.setToken(token);
        list.add(entity);
        return list;
    }

    /**
     * 获取百度用户id
     *
     * @param username
     * @param password
     * @param token
     * @return
     */
    private Long getBaiduAccountId(String username, String password, String token) {
        CommonService service = null;
        Long baiduAccountId = null;
        try {
            service = ServiceFactory.getInstance(username, password, token, null);
            AccountService accountService = service.getService(AccountService.class);
            GetAccountInfoRequest request = new GetAccountInfoRequest();
            GetAccountInfoResponse response = accountService.getAccountInfo(request);
            AccountInfoType accountInfoType = response.getAccountInfoType();
            baiduAccountId = accountInfoType.getUserid();
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return baiduAccountId;
    }
}
