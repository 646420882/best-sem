package com.perfect.service.impl;

import com.perfect.dao.sys.SystemLogDAO;
import com.perfect.dto.sys.SystemLogDTO;
import com.perfect.param.SystemLogParams;
import com.perfect.service.SystemLogService;
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
    public List<SystemLogDTO> list(SystemLogParams params, int page, int size, String s, boolean b) {


        return systemLogDAO.list(params, page, size, s, b);
    }
}
