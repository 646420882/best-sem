package com.perfect.service;

import com.perfect.dto.sys.SystemLogDTO;
import com.perfect.param.SystemLogParams;

import java.util.List;

/**
 * Created by yousheng on 15/12/16.
 */
public interface SystemLogService {
    List<SystemLogDTO> list(SystemLogParams params, int page, int size, String s, boolean b);
}
