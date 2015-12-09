package com.perfect.service.impl;

import com.perfect.account.BaseBaiduAccountInfoVO;
import com.perfect.api.baidu.BaiduServiceSupport;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.GetKeyword10QualityRequest;
import com.perfect.autosdk.sms.v3.GetKeyword10QualityResponse;
import com.perfect.autosdk.sms.v3.KeywordService;
import com.perfect.autosdk.sms.v3.Quality10Type;
import com.perfect.core.AppContext;
import com.perfect.service.Keyword10QualityService;
import com.perfect.service.SystemUserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-9-18.
 * 2014-11-26 refactor
 */
@Service("keyword10QualityService")
public class Keyword10QualityServiceImpl implements Keyword10QualityService {

    @Resource
    private SystemUserInfoService systemUserInfoService;

    @Override
    @SuppressWarnings("unchecked")
    public Map<Long, Quality10Type> getKeyword10Quality(List<Long> keywordIds) {
        BaseBaiduAccountInfoVO baiduAccount = systemUserInfoService.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(baiduAccount.getAccountName(), baiduAccount.getPassword(), baiduAccount.getToken());
        try {
            KeywordService keywordService = commonService.getService(KeywordService.class);
            GetKeyword10QualityRequest request = new GetKeyword10QualityRequest();
            request.setIds(keywordIds);
            request.setDevice(2);
            request.setType(11);
            request.setHasScale(false);
            GetKeyword10QualityResponse response = keywordService.getKeyword10Quality(request);

            if (response == null) {
                return Collections.EMPTY_MAP;
            }

            Map<Long, Quality10Type> map = new HashMap<>();
            for (Quality10Type type : response.getKeyword10Quality()) {
                map.put(type.getId(), type);
            }

            return map;
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return null;
    }

}
