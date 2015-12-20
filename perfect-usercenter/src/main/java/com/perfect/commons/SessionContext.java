package com.perfect.commons;

import com.perfect.commons.constants.UserConstants;
import com.perfect.dto.sys.ModuleAccountInfoDTO;
import com.perfect.dto.sys.SystemUserDTO;
import com.perfect.dto.sys.SystemUserModuleDTO;
import com.perfect.utils.RedisObtainedByToken;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by subdong on 15-12-20.
 */
public class SessionContext {


    /**
     * 获取当前登录用户信息
     *
     * @param request
     * @return
     */
    public static SystemUserDTO getUser(HttpServletRequest request) {
        return userInfo(request);
    }

    public static List<SystemUserModuleDTO> getUserModule(HttpServletRequest request) {
        SystemUserDTO systemUserDTO = userInfo(request);
        return systemUserDTO.getSystemUserModules();
    }

    public static List<ModuleAccountInfoDTO> getUserAccountinfo(HttpServletRequest request, String moduleName) {
        SystemUserDTO systemUserDTO = userInfo(request);
        if (Objects.nonNull(systemUserDTO)) {
            List<ModuleAccountInfoDTO> moduleAccountInfoDTOs = systemUserDTO.getSystemUserModules().stream()
                    .filter(e -> e.getModuleName().equals(moduleName))
                    .map(s -> s.getAccounts()).reduce((a, b) -> {
                        b.addAll(a);
                        return b;
                    }).get();
            return moduleAccountInfoDTOs;
        }
        return null;
    }

    private static SystemUserDTO userInfo(HttpServletRequest request) {
        return (SystemUserDTO) request.getSession().getAttribute(UserConstants.SESSION_USER);
    }
}
