package com.perfect.admin.controllers;

import com.google.common.base.Strings;
import com.perfect.admin.utils.JsonViews;
import com.perfect.commons.web.JsonResultMaps;
import com.perfect.dto.sys.SystemLogDTO;
import com.perfect.param.SystemLogParams;
import com.perfect.service.SystemLogService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yousheng on 15/12/16.
 */
@RestController
public class SystemLogController {

    private final String DEFAULT_SORT = "time";


    @Resource
    private SystemLogService systemLogService;

    @RequestMapping(value = "/syslogs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView list(@RequestParam(value = "page", required = false) int page,
                             @RequestParam(value = "size", required = false) int size,
                             @RequestParam(value = "sort", required = false) String sort,
                             @RequestParam(value = "asc", required = false) Boolean asc,
                             @RequestParam(value = "start", required = false) Long start,
                             @RequestParam(value = "end", required = false) Long end,
                             @RequestParam(value = "user", required = false) String user) {

        if (page < 0 || size <= 0) {
            return JsonViews.generate(-1, "分页参数不合法.");
        }

        SystemLogParams params = new SystemLogParams().setStart(start).setEnd(end).setUser(user);
        List<SystemLogDTO> systemLogDTOList = systemLogService.list(params, page, size, (Strings
                .isNullOrEmpty(sort)) ? DEFAULT_SORT : sort, (asc == null) ? false : asc);


        if (systemLogDTOList == null || systemLogDTOList.isEmpty()) {
            return JsonViews.generateSuccessNoData();
        }

        return JsonViews.generate(JsonResultMaps.successMap(systemLogDTOList));
    }

}
