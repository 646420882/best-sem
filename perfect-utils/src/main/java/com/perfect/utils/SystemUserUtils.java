package com.perfect.utils;

import com.google.common.collect.Lists;
import com.perfect.dto.sys.ModuleAccountInfoDTO;
import com.perfect.dto.sys.SystemUserDTO;
import com.perfect.dto.sys.SystemUserModuleDTO;
import com.perfect.entity.sys.SystemUserEntity;
import com.perfect.entity.sys.SystemUserModuleEntity;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by dolphineor on 15-12-18.
 */
public class SystemUserUtils {


    public static SystemUserEntity retrieveEntityFromDTO(SystemUserDTO dto) {
        SystemUserEntity systemUserEntity = new SystemUserEntity();
        BeanUtils.copyProperties(dto, systemUserEntity);

        List<SystemUserModuleEntity> systemUserModules = new ArrayList<>();
        for (SystemUserModuleDTO userModuleDTO : dto.getSystemUserModules()) {
            SystemUserModuleEntity systemUserModuleEntity = new SystemUserModuleEntity();
            BeanUtils.copyProperties(userModuleDTO, systemUserModuleEntity);

            systemUserModules.add(systemUserModuleEntity);
        }
        systemUserEntity.setSystemUserModules(systemUserModules);

        return systemUserEntity;
    }


    public static List<ModuleAccountInfoDTO> findAccountsBySystemName(SystemUserDTO systemUserDTO, String systemName) {
        final List<ModuleAccountInfoDTO> moduleAccountInfoDTOs = Lists.newArrayList();
        systemUserDTO.getSystemUserModules().stream().filter((systemUserModuleDTO -> systemUserModuleDTO.getModuleName().equals(systemName)))
                .findFirst()
                .ifPresent(systemUserModuleDTO1 -> {
            moduleAccountInfoDTOs.addAll(systemUserModuleDTO1.getAccounts());
        });

        return moduleAccountInfoDTOs;
    }

    public static void consumeCurrentSystemAccount(SystemUserDTO systemUserDTO, String systemName, Consumer<SystemUserModuleDTO> consumer) {
        systemUserDTO.getSystemUserModules().stream().filter((systemUserModuleDTO -> systemUserModuleDTO.getModuleName().equals(systemName))).findFirst().ifPresent(consumer);
    }
}
