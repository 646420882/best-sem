package com.perfect.admin.controllers;

import com.google.common.base.Strings;
import com.perfect.admin.utils.JsonViews;
import com.perfect.commons.web.JsonResultMaps;
import com.perfect.dto.sys.SystemUserDTO;
import com.perfect.service.SystemUserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 用户管理控制器
 * Created by yousheng on 15/12/15.
 */
@RestController
public class UserManageController {

    @Resource
    private SystemUserService systemUserService;

    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView listUser(@RequestParam(value = "company", required = false) String companyName,
                                 @RequestParam(value = "user", required = false) String userName,
                                 @RequestParam(value = "account", required = false) Boolean accountStatus,
                                 @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                 @RequestParam(value = "pagesize", required = false, defaultValue = "20") int pagesize,
                                 @RequestParam(value = "order", required = false, defaultValue = "ctime") String order,
                                 @RequestParam(value = "asc", required = false, defaultValue = "false") boolean asc) {
        List<SystemUserDTO> systemUserDTOList = systemUserService.findUsers(companyName, userName, accountStatus,
                (page - 1) * pagesize, pagesize, order, asc);


        if (systemUserDTOList == null || systemUserDTOList.isEmpty()) {
            return JsonViews.generateSuccessNoData();
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        systemUserDTOList.forEach((dto) -> {
            dto.setPassword(null);

            // format date
            date.setTime(dto.getCtime());
            dto.setDisplayCtime(format.format(date));
        });

        return JsonViews.generate(JsonResultMaps.successMap(systemUserDTOList));

    }

    /**
     * 开通账号
     *
     * @param id
     * @param accountStatus
     * @return
     */
    @RequestMapping(value = "/users/{id}/status", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView changeAccountStatus(@PathVariable("id") String id, @RequestParam(value = "status") Integer
            accountStatus) {

        if (accountStatus == null) {
            return new ModelAndView();
        }

        boolean success = systemUserService.updateAccountStatus(id, accountStatus);

        if (success) {
            return JsonViews.generateSuccessNoData();
        }

        return JsonViews.generate(-1);
    }

    @RequestMapping(value = "/users/{id}/payed", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView changeAccountPayed(@PathVariable("id") String id,
                                           @RequestParam(value = "payed") Boolean payed) {

        boolean success = systemUserService.updateAccountPayed(id, payed);

        if (success) {
            return JsonViews.generateSuccessNoData();
        }

        return JsonViews.generate(-1);
    }

    @RequestMapping(value = "/users/{userid}/password", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView resetUserPassword(@PathVariable("userid") String userid, @RequestParam(value = "password", required = false, defaultValue = "Abcd1234") String password) {
        boolean success = systemUserService.updateUserPassword(userid, password);

        if (success) {
            return JsonViews.generateSuccessNoData();
        }
        return JsonViews.generateFailedNoData();

    }


    @RequestMapping(value = "/users/{id}/time", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView updateAccountTime(@PathVariable("id") String id,
                                          @RequestParam(value = "start", required = false) String start,
                                          @RequestParam(value = "end", required = false) String end) {

        if (Strings.isNullOrEmpty(start) && Strings.isNullOrEmpty(end)) {
            return JsonViews.generate(-1, "请设置开始或者结束时间");
        }

        Date startDate = parseDate(start);

        if (startDate == null) {
            return JsonViews.generate(-1, "起始时间格式错误.请使用(yyyy MM dd)/(yyyy-MM-dd)/(yyyy/MM/dd)");
        }

        Date endDate = parseDate(end);

        if (endDate == null) {
            return JsonViews.generate(-1, "结束时间格式错误.请使用(yyyy MM dd)/(yyyy-MM-dd)/(yyyy/MM/dd)");
        }


        boolean success = systemUserService.updateAccountTime(id, startDate, endDate);

        if (success) {
            return JsonViews.generateSuccessNoData();
        }

        return JsonViews.generate(-1);
    }


    private Date parseDate(String date) {
        if (!Strings.isNullOrEmpty(date)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd");
            try {
                return format.parse(date);
            } catch (ParseException e) {
                format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    return format.parse(date);
                } catch (ParseException e1) {
                    format = new SimpleDateFormat("yyyy/MM/dd");
                    try {
                        return format.parse(date);
                    } catch (ParseException e2) {
                        return null;
                    }
                }
            }
        }
        return null;
    }

}
