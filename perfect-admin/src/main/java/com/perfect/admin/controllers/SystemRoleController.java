package com.perfect.admin.controllers;

import com.perfect.admin.utils.JsonViews;
import com.perfect.commons.web.JsonResultMaps;
import com.perfect.dto.sys.SystemRoleDTO;
import com.perfect.service.SystemRoleService;
import com.perfect.utils.MD5;
import org.elasticsearch.common.Strings;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yousheng on 15/12/17.
 */
@RestController
public class SystemRoleController {

    @Resource
    private SystemRoleService systemRoleService;

    @RequestMapping(value = "/sysroles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView list(
            @RequestParam(value = "name", required = false) String queryName,
            @RequestParam(value = "super", required = false) Boolean superUser,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "20") Integer size,
            @RequestParam(value = "sort", required = false, defaultValue = "name") String sort,
            @RequestParam(value = "order", required = false, defaultValue = "true") Boolean asc) {

        if (page < 1 || size < 0) {
            return JsonViews.generate(-1, "分页参数错误.");
        }

        List<SystemRoleDTO> systemRoleDTOList = systemRoleService.list(queryName, superUser, page, size, sort, asc);
        return JsonViews.generate(JsonResultMaps.successMap(systemRoleDTOList));
    }

    @RequestMapping(value = "/sysroles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView createSystemRole(@RequestBody SystemRoleDTO systemRoleDTO) {

        systemRoleDTO.setPassword(new MD5.Builder().password(systemRoleDTO.getPassword()).salt("role_password").build().getMD5());
        systemRoleService.addSystemRole(systemRoleDTO);
        return JsonViews.generateSuccessNoData();
    }


    @RequestMapping(value = "/sysroles/{roleid}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView createSystemRole(@PathVariable("roleid") String roleid, @RequestBody SystemRoleDTO systemRoleDTO) {

        if(!Strings.isNullOrEmpty(systemRoleDTO.getPassword())){
            systemRoleDTO.setPassword(new MD5.Builder().password(systemRoleDTO.getPassword()).salt("role_password").build().getMD5());
        }
        systemRoleService.update(roleid, systemRoleDTO);
        return JsonViews.generateSuccessNoData();
    }

}
