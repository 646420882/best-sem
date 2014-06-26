package com.perfect.app.accountCenter.dao.impl;

import com.perfect.app.accountCenter.dao.AccountManageDAO;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.AccountInfoType;
import com.perfect.autosdk.sms.v3.AccountService;
import com.perfect.autosdk.sms.v3.GetAccountInfoRequest;
import com.perfect.autosdk.sms.v3.GetAccountInfoResponse;
import com.perfect.mongodb.entity.BaiduAccountInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baizz on 2014-6-25.
 */
@Repository("accountManageDAO")
public class AccountManageDAOImpl implements AccountManageDAO<BaiduAccountInfo> {

    /**
     * 百度账户树
     *
     * @param list
     * @return
     */
    @Override
    public JSONArray getAccountTree(List<BaiduAccountInfo> list) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject;
        for (BaiduAccountInfo account : list) {
            jsonObject = new JSONObject();
            jsonObject.put("id", account.getId());
            jsonObject.put("pId", 0);
            jsonObject.put("name", account.getBaiduUserName());
            jsonArray.add(jsonObject);
            jsonObject = null;
        }
        return jsonArray;
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

    /**
     * @param username
     * @param password
     * @param token
     * @return
     */
    @Override
    public List<BaiduAccountInfo> getBaiduAccountInfos(String username, String password, String token) {
        List<BaiduAccountInfo> list = new ArrayList<>();
        Long id = getBaiduAccountId(username, password, token);
        BaiduAccountInfo entity = new BaiduAccountInfo();
        entity.setId(id);
        entity.setBaiduUserName(username);
        entity.setBaiduPassword(password);
        entity.setToken(token);
        list.add(entity);
        return list;
    }
}
