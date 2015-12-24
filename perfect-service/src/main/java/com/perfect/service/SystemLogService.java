package com.perfect.service;

import com.perfect.dto.sys.SystemLogDTO;
import com.perfect.param.SystemLogParams;
import com.perfect.utils.paging.BootStrapPagerInfo;
import com.perfect.utils.paging.PagerInfo;

import java.util.List;

/**
 * Created by yousheng on 15/12/16.
 */
public interface SystemLogService {
    BootStrapPagerInfo list(SystemLogParams params, int offset, int limit, String s, String order);

    Long getListTotalCount(SystemLogParams slp);
}
