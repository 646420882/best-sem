package com.perfect.admin.controllers;

import com.google.common.base.Strings;
import com.perfect.admin.utils.JsonViews;
import com.perfect.commons.web.JsonResultMaps;
import com.perfect.dto.huiyan.InsightWebsiteDTO;
import com.perfect.dto.sys.SystemUserDTO;
import com.perfect.service.SystemUserService;
import com.perfect.service.UserAccountService;
import com.perfect.utils.paging.BootStrapPagerInfo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 用户管理控制器
 * Created by yousheng on 15/12/15.
 */
@RestController
public class UserManageController {

    @Resource
    private SystemUserService systemUserService;

    @Resource
    private UserAccountService userAccountService;

    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public BootStrapPagerInfo listUser(@RequestParam(value = "company", required = false) String companyName,
                                       @RequestParam(value = "user", required = false) String userName,
                                       @RequestParam(value = "account", required = false) Boolean accountStatus,
                                       @RequestParam(value = "offset", required = false, defaultValue = "1") int page,
                                       @RequestParam(value = "limit", required = false, defaultValue = "20") int pagesize,
                                       @RequestParam(value = "sort", required = false, defaultValue = "ctime") String sort,
                                       @RequestParam(value = "order", required = false, defaultValue = "false") String oder) {
        boolean asc = oder.equals("asc");
        BootStrapPagerInfo bootStrapPagerInfo = systemUserService.findUsersPageable(companyName, userName, accountStatus, page, pagesize, sort, asc);


        if (bootStrapPagerInfo == null) {
            return BootStrapPagerInfo.buildErrorInfo(0, "");
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        bootStrapPagerInfo.getRows().forEach((obj) -> {
            SystemUserDTO dto = (SystemUserDTO) obj;
            dto.setPassword(null);

            // format date
            date.setTime(dto.getCtime());
            dto.setDisplayCtime(format.format(date));
        });

        return bootStrapPagerInfo;
    }

    @RequestMapping(value = "/usersHuiYan", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView usersHuiYan(@RequestParam(value = "uid", required = false) String uid) {

        List<InsightWebsiteDTO> insightWebsiteDTOs = userAccountService.queryInfo(uid);
        if (insightWebsiteDTOs == null) {
            return JsonViews.generate(-1);
        }

        return JsonViews.generate(JsonResultMaps.successMap(insightWebsiteDTOs));
    }

    /**
     * 获取用户ID
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/users/{username}/getId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getSystemUserId(@PathVariable String username) {
        AbstractView jsonView = new MappingJackson2JsonView();
        String userId = userAccountService.getUserId(username);
        jsonView.setAttributesMap(new HashMap<String, Object>() {{
            put("userId", userId == null ? "" : userId);
        }});

        return new ModelAndView(jsonView);
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
