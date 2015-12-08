package com.perfect.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.perfect.account.BaseBaiduAccountInfoVO;
import com.perfect.account.SystemUserInfoVO;
import com.perfect.core.AppContext;
import com.perfect.service.SystemUserInfoService;
import com.perfect.utils.http.HttpClientUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created on 2015-12-07.
 *
 * @author dolphineor
 */
@Service("systemUserInfoService")
public class SystemUserInfoServiceImpl implements SystemUserInfoService {

    private final ResourceBundle bundle = ResourceBundle.getBundle("apitoken");


    @Override
    public List<SystemUserInfoVO> findAllSystemUserAccount() {
        try {
            String data = accountRequest(RETRIEVE_ALL_SYSTEM_USER_URL, Maps.newHashMap());
            if (Objects.nonNull(data)) {
                List<SystemUserInfoVO> sysUsers = new ArrayList<>();

                JSONArray jsonArray = JSON.parseArray(data);
                for (int i = 0, s = jsonArray.size(); i < s; i++) {
                    JSONObject o = jsonArray.getJSONObject(i);
                    if (o.getIntValue("access") == 1)
                        continue;

                    sysUsers.add(extractUserInfoFromJSON(o));
                }

                return sysUsers;
            }

            return Collections.emptyList();
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    @Override
    public SystemUserInfoVO findSystemUserInfoByUserName(String username) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("username", username);

        return sysUserTransform(RETRIEVE_SPECIFIC_SYSTEM_USER_BY_USERNAME_URL, params);
    }

    @Override
    public BaseBaiduAccountInfoVO findByBaiduUserId(Long baiduUserId) {
        for (BaseBaiduAccountInfoVO baiduAccountInfoVO : AppContext.getBaiduAccounts()) {
            if (Objects.equals(baiduUserId, baiduAccountInfoVO.getAccountId())) {
                return baiduAccountInfoVO;
            }
        }

        return null;
    }

    @Override
    public List<BaseBaiduAccountInfoVO> findBaiduAccountsByUserName(String username) {
        return findSystemUserInfoByUserName(username).getBaiduAccounts();
    }

    @Override
    public SystemUserInfoVO findSystemUserInfoByBaiduAccountId(Long baiduUserId) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("bdid", baiduUserId);

        return sysUserTransform(RETRIEVE_SPECIFIC_SYSTEM_USER_BY_BAIDU_ACCOUNT_ID_URL, params);
    }

    @Override
    public List<BaseBaiduAccountInfoVO> findAllBaiduAccounts() {
        List<SystemUserInfoVO> validSystemUsers = findAllSystemUserAccount()
                .stream()
                .filter(sysUser -> {
                    boolean isValidSysUser = sysUser.getStatus() == 1
                            && sysUser.getAccountStatus() == 1
                            && sysUser.getAccess() == 2;

                    return isValidSysUser;
                })
                .collect(Collectors.toList());

        List<BaseBaiduAccountInfoVO> baiduAccounts = new ArrayList<>();
        for (SystemUserInfoVO sysUser : validSystemUsers) {
            baiduAccounts.addAll(sysUser.getBaiduAccounts());
        }

        return baiduAccounts;
    }


    /**
     * <p>发送请求并获取解密后的JSON数据.
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    private String accountRequest(String url, Map<String, Object> params) throws Exception {
        params.put(PARAM_SERVICE_TOKEN, bundle.getString("api.service.token"));
        String result = HttpClientUtils.postRequest(url, params);
        if (Objects.nonNull(result)) {
            JSONObject jsonObject = JSON.parseObject(result);

            if (jsonObject.getBooleanValue("success")) {
                return decrypt(jsonObject.getString("data"), PASSWORD_CRYPT_KEY);
            }

            return null;
        }

        return null;
    }

    /**
     * <p>从JSON数据中提取系统用户信息.
     *
     * @param jsonObject
     * @return
     */
    private SystemUserInfoVO extractUserInfoFromJSON(JSONObject jsonObject) {
        String username = jsonObject.getString("userName");
        String imageUrl = jsonObject.getString("img");
        int status = jsonObject.getIntValue("state");
        int accountStatus = jsonObject.getIntValue("accountState");
        int access = jsonObject.getIntValue("access");
        JSONArray bdAccountArr = jsonObject.getJSONArray("baiduAccounts");

        SystemUserInfoVO userInfo = new SystemUserInfoVO();
        userInfo.setUsername(username);
        userInfo.setImageUrl(imageUrl);
        userInfo.setStatus(status);
        userInfo.setAccountStatus(accountStatus);
        userInfo.setAccess(access);

        List<BaseBaiduAccountInfoVO> baseBaiduAccounts = new ArrayList<>();
        for (int i = 0, s = bdAccountArr.size(); i < s; i++) {
            baseBaiduAccounts.add(new BaseBaiduAccountInfoVO(
                    bdAccountArr.getJSONObject(i).getLong("bdAccountID"),
                    bdAccountArr.getJSONObject(i).getString("bdfcName"),
                    bdAccountArr.getJSONObject(i).getString("defaultName"),
                    bdAccountArr.getJSONObject(i).getString("bdfcPwd"),
                    bdAccountArr.getJSONObject(i).getString("bdToken"),
                    bdAccountArr.getJSONObject(i).getBoolean("bddefault")
            ));
        }
        userInfo.setBaiduAccounts(baseBaiduAccounts);

        return userInfo;
    }

    private SystemUserInfoVO sysUserTransform(String url, Map<String, Object> params) {
        try {
            String data = accountRequest(url, params);
            if (Objects.nonNull(data)) {
                return extractUserInfoFromJSON(JSON.parseObject(data));
            }

            return null;
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
