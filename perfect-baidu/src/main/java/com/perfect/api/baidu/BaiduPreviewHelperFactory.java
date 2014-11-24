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


    public BaiduSpiderHelper createInstance(CommonService commonService){
        BaiduSpiderHelper baiduSpiderHelper = new BaiduSpiderHelper(commonService);

        baiduSpiderHelper.setContext(applicationContextHelper);
        return baiduSpiderHelper;
    }
}
