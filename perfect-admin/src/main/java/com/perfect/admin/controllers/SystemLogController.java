package com.perfect.admin.controllers;

import com.google.common.base.Strings;
import com.perfect.admin.utils.JsonViews;
import com.perfect.admin.utils.SuperUserUtils;
import com.perfect.commons.web.JsonResultMaps;
import com.perfect.core.AppContext;
import com.perfect.dto.sys.SystemLogDTO;
import com.perfect.param.SystemLogParams;
import com.perfect.service.SystemLogService;
import com.perfect.utils.paging.BootStrapPagerInfo;
import com.perfect.utils.paging.PagerInfo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 日志查看控制器
 * Created by yousheng on 15/12/16.
 */
@RestController
public class SystemLogController {

    private final String DEFAULT_SORT = "time";


    @Resource
    private SystemLogService systemLogService;

    @RequestMapping(value = "/syslogs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BootStrapPagerInfo list(@RequestParam(value = "offset", required = false) int offset,
                                   @RequestParam(value = "limit", required = false) int limit,
                                   @RequestParam(value = "sort", required = false, defaultValue = "time") String sort,
                                   @RequestParam(value = "order", required = false, defaultValue = "false") String order,
                                   @RequestParam(value = "start", required = false) Long start,
                                   @RequestParam(value = "end", required = false) Long end,
                                   @RequestParam(value = "search", required = false) String user) {


        boolean isSuper = SuperUserUtils.isLoginSuper();
        SystemLogParams params = new SystemLogParams().setStart(start).setEnd(end).setUser(user);

        if (!isSuper) {
            params.setUser(AppContext.getSystemUserInfo().getUser());
        }

        if (offset < 0 || limit <= 0) {
            return new BootStrapPagerInfo();
        }

        BootStrapPagerInfo p = systemLogService.list(params, offset, limit, (Strings
                .isNullOrEmpty(sort)) ? DEFAULT_SORT : sort, (order == null) ? order : "desc");


        if (p == null) {
            if (p.getRows() == null || p.getRows().isEmpty()) {
                return new BootStrapPagerInfo();
            }
        }

        return p;
    }

}
