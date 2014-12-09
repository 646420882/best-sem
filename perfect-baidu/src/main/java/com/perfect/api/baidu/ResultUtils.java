package com.perfect.api.baidu;

import com.perfect.autosdk.core.ResHeader;
import com.perfect.autosdk.core.ResHeaderUtil;
import com.perfect.autosdk.exception.ApiException;

/**
 * 检查api调用的返回结果
 * @author yousheng
 */
public class ResultUtils {

    public static boolean isFailed(Object proxy) throws ApiException {
        ResHeader resHeader = ResHeaderUtil.getResHeader(proxy, false);
        return resHeader.getFailures().size() > 0;

    }
}
