package com.perfect.service.impl;

import com.perfect.dao.sys.SystemLogDAO;
import com.perfect.param.SystemLogParams;
import com.perfect.service.SystemLogService;
import com.perfect.utils.paging.BootStrapPagerInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yousheng on 15/12/16.
 */
@Service
public class SystemLogServiceImpl implements SystemLogService {
    @Resource
    private SystemLogDAO systemLogDAO;

    @Override
    public BootStrapPagerInfo list(SystemLogParams params, int offset, int limit, String s, String  order) {
        return systemLogDAO.list(params, offset, limit, s, order);
    }
}
