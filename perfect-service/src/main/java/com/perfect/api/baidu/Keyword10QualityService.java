package com.perfect.api.baidu;

import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.GetKeyword10QualityRequest;
import com.perfect.autosdk.sms.v3.GetKeyword10QualityResponse;
import com.perfect.autosdk.sms.v3.KeywordService;
import com.perfect.autosdk.sms.v3.Quality10Type;
import com.perfect.core.AppContext;
import com.perfect.service.AccountManageService;
import com.perfect.utils.BaiduServiceSupport;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-9-18.
 */
@Component("keyword10QualityService")
public class Keyword10QualityService {

    @Resource
    private AccountManageService accountManageService;

    public Map<Long, Quality10Type> getKeyword10Quality(List<Long> keywordIds) {
        CommonService commonService = BaiduServiceSupport.getCommonService(accountManageService.getBaiduAccountInfoById(AppContext.getAccountId()));
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
