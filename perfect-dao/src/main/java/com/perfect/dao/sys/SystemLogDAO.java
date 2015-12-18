package com.perfect.dao.sys;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.sys.SystemLogDTO;
import com.perfect.param.SystemLogParams;

import java.util.List;

/**
 * Created by yousheng on 15/12/16.
 */
public interface SystemLogDAO extends HeyCrudRepository<SystemLogDTO, String> {
    List<SystemLogDTO> list(SystemLogParams params, int page, int size, String sort, boolean asc);
    void log(String txt);
}
