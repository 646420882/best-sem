package com.perfect.admin.controllers;

import com.google.common.base.Strings;
import com.perfect.admin.utils.JsonViews;
import com.perfect.admin.utils.SuperUserUtils;
import com.perfect.commons.web.JsonResultMaps;
import com.perfect.core.AppContext;
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
    public ModelAndView list(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                             @RequestParam(value = "size", required = false, defaultValue = "20") int size,
                             @RequestParam(value = "sort", required = false, defaultValue = "time") String sort,
                             @RequestParam(value = "asc", required = false, defaultValue = "false") Boolean asc,
                             @RequestParam(value = "start", required = false) Long start,
                             @RequestParam(value = "end", required = false) Long end,
                             @RequestParam(value = "user", required = false) String user) {


        boolean isSuper = SuperUserUtils.isLoginSuper();
        SystemLogParams params = new SystemLogParams().setStart(start).setEnd(end).setUser(user);

        if (!isSuper) {
            params.setUser(AppContext.getSystemUserInfo().getUser());
        }

        if (page < 0 || size <= 0) {
            return JsonViews.generate(-1, "分页参数不合法.");
        }

        List<SystemLogDTO> systemLogDTOList = systemLogService.list(params, page, size, (Strings
                .isNullOrEmpty(sort)) ? DEFAULT_SORT : sort, (asc == null) ? false : asc);


        if (systemLogDTOList == null || systemLogDTOList.isEmpty()) {
            return JsonViews.generateSuccessNoData();
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        systemLogDTOList.forEach((systemLogDTO -> {
            systemLogDTO.setDisplayTime(formatter.format(new Date(systemLogDTO.getTime())));
        }));

        return JsonViews.generate(JsonResultMaps.successMap(systemLogDTOList));
    }

}
