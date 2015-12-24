package com.perfect.dao.sys;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.sys.SystemLogDTO;
import com.perfect.param.SystemLogParams;
import com.perfect.utils.paging.BootStrapPagerInfo;
import com.perfect.utils.paging.PagerInfo;

import java.util.List;

/**
 * Created by yousheng on 15/12/16.
 */
public interface SystemLogDAO extends HeyCrudRepository<SystemLogDTO, String> {
    List<SystemLogDTO> list(SystemLogParams params, int offset, int limit, String sort, String order);

    void log(String txt);

    Long getListTotalCount(SystemLogParams slp);
}
