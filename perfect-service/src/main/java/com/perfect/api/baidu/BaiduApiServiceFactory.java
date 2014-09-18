package com.perfect.api.baidu;

import com.perfect.autosdk.core.CommonService;
import com.perfect.commons.context.ApplicationContextHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by vbzer_000 on 14-9-18.
 */
@Component
public class BaiduApiServiceFactory {

    @Resource
    private ApplicationContextHelper applicationContextHelper;


    @Resource
    private BaiduPreviewHelperFactory baiduPreviewHelperFactory;

    public BaiduApiService createService(CommonService commonService){
        BaiduApiService baiduApiService = new BaiduApiService(commonService);

        baiduApiService.setContext(applicationContextHelper);

        baiduApiService.setBaiduPreviewHelperFactory(baiduPreviewHelperFactory);
        return baiduApiService;

    }
}
