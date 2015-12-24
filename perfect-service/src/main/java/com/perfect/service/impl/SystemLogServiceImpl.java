package com.perfect.service.impl;

import com.perfect.dao.sys.SystemLogDAO;
import com.perfect.dto.sys.SystemLogDTO;
import com.perfect.param.SystemLogParams;
import com.perfect.service.SystemLogService;
import com.perfect.utils.paging.BootStrapPagerInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by yousheng on 15/12/16.
 */
@Service
public class SystemLogServiceImpl implements SystemLogService {
    @Resource
    private SystemLogDAO systemLogDAO;

    @Override
    public BootStrapPagerInfo list(SystemLogParams params, int offset, int limit, String s, String order) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        List<SystemLogDTO> systemLogDTOs = systemLogDAO.list(params, offset, limit, s, order);
        long totalCount = getListTotalCount(params);
        if (systemLogDTOs != null) {
            if (systemLogDTOs.size() > 0)
                systemLogDTOs.forEach((systemLogDTO -> {
                    systemLogDTO.setDisplayTime(formatter.format(new Date(systemLogDTO.getTime())));
                }));
            return new BootStrapPagerInfo(totalCount, systemLogDTOs);
        }
        return null;
    }

    @Override
    public Long getListTotalCount(SystemLogParams slp) {
        return systemLogDAO.getListTotalCount(slp);
    }
}
