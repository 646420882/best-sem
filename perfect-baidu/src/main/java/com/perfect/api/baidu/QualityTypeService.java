package com.perfect.api.baidu;

import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.GetKeywordQualityRequest;
import com.perfect.autosdk.sms.v3.GetKeywordQualityResponse;
import com.perfect.autosdk.sms.v3.KeywordService;
import com.perfect.autosdk.sms.v3.QualityType;
import com.perfect.core.AppContext;
import com.perfect.service.AccountManageService;
import com.perfect.utils.BaiduServiceSupport;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 2014/9/18.
 * 根据关键词id在百度上获取质量度
 */

@Component("qualityTypeService")
public class QualityTypeService {

    @Resource
    private AccountManageService accountManageService;

    public List<QualityType> getQualityType(List<Long> keywordIds) {
        CommonService commonService = BaiduServiceSupport.getCommonService(accountManageService.getBaiduAccountInfoById(AppContext.getAccountId()));
        List<QualityType> list = new ArrayList<>();

        try {
            KeywordService keywordService = commonService.getService(KeywordService.class);
            GetKeywordQualityRequest request = new GetKeywordQualityRequest();
            request.setIds(keywordIds);
            request.setType(11);//3: 表示指定id数组为计划id 5:表示指定id数组为单元id 11:表示指定id为关键词id

            GetKeywordQualityResponse response = keywordService.getKeywordQuality(request);

            if (response == null) {
                return list;
            }
            list = response.getQualities();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return list;
    }

}
