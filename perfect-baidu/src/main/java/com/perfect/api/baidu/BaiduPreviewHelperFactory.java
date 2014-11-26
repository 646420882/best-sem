package com.perfect.api.baidu;

import com.perfect.autosdk.core.CommonService;
import com.perfect.commons.context.ApplicationContextHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by vbzer_000 on 14-9-18.
 */
@Component
public class BaiduPreviewHelperFactory {

    @Resource
    private ApplicationContextHelper applicationContextHelper;


    public BaiduPreviewHelper createInstance(CommonService commonService){
        BaiduPreviewHelper baiduPreviewHelper = new BaiduPreviewHelper(commonService);

        baiduPreviewHelper.setContext(applicationContextHelper);
        return baiduPreviewHelper;
    }
}
