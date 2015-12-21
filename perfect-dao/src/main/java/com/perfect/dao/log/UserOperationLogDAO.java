package com.perfect.dao.log;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.log.UserOperationLogDTO;
import com.perfect.param.SystemLogParams;
import com.perfect.utils.paging.PagerInfo;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * Created by yousheng on 2015/12/13.
 */
public interface UserOperationLogDAO extends HeyCrudRepository<UserOperationLogDTO, String> {



    /**
     *
     * @param start 开始时间戳
     * @param end 结束时间戳
     * @param pageNo 开始页
     * @param pageSize 每页显示条数
     * @param oid 层级id
     * @param level 层级标识
     * @return
     */
    PagerInfo queryLog(Long start, Long end, Integer pageNo, Integer pageSize, List<Long> oid, Integer level);


    /**
     * 查询对象
     * @param slp
     * @return
     */
    PagerInfo queryLog(SystemLogParams slp);
}
