package com.perfect.utils;

import com.google.common.collect.Lists;
import com.perfect.dto.sys.ModuleAccountInfoDTO;
import com.perfect.dto.sys.SystemMenuDTO;
import com.perfect.dto.sys.SystemUserDTO;
import com.perfect.dto.sys.SystemUserModuleDTO;
import com.perfect.entity.sys.ModuleAccountInfoEntity;
import com.perfect.entity.sys.SystemMenuEntity;
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
        List<SystemMenuEntity> menus = new ArrayList<>();
        List<ModuleAccountInfoEntity> accounts = new ArrayList<>();

        List<SystemUserModuleDTO> systemUserModuleDTOs = dto.getSystemUserModules();
        for (SystemUserModuleDTO systemUserModuleDTO : systemUserModuleDTOs) {
            menus.clear();
            accounts.clear();

            SystemUserModuleEntity systemUserModuleEntity = ObjectUtils.convert(systemUserModuleDTO, SystemUserModuleEntity.class);

            List<SystemMenuDTO> systemMenuDTOs = systemUserModuleDTO.getMenus();
            for (SystemMenuDTO systemMenuDTO : systemMenuDTOs) {
                menus.add(ObjectUtils.convert(systemMenuDTO, SystemMenuEntity.class));
            }

            List<ModuleAccountInfoDTO> moduleAccountInfoDTOs = systemUserModuleDTO.getAccounts();
            for (ModuleAccountInfoDTO moduleAccountInfoDTO : moduleAccountInfoDTOs) {
                accounts.add(ObjectUtils.convert(moduleAccountInfoDTO, ModuleAccountInfoEntity.class));
            }

            systemUserModuleEntity.setMenus(menus);
            systemUserModuleEntity.setAccounts(accounts);

            systemUserModules.add(systemUserModuleEntity);
        }

        systemUserEntity.setSystemUserModules(systemUserModules);

        return systemUserEntity;
    }

    public static SystemUserDTO retrieveDTOFromEntity(SystemUserEntity entity) {
        SystemUserDTO systemUserDTO = new SystemUserDTO();
        BeanUtils.copyProperties(entity, systemUserDTO);

        List<SystemUserModuleDTO> systemUserModules = new ArrayList<>();
        List<SystemMenuDTO> menus = new ArrayList<>();
        List<ModuleAccountInfoDTO> accounts = new ArrayList<>();

        List<SystemUserModuleEntity> systemUserModuleEntities = entity.getSystemUserModules();
        for (SystemUserModuleEntity systemUserModuleEntity : systemUserModuleEntities) {
            menus.clear();
            accounts.clear();

            SystemUserModuleDTO systemUserModuleDTO = ObjectUtils.convert(systemUserModuleEntity, SystemUserModuleDTO.class);

            List<SystemMenuEntity> systemMenuEntities = systemUserModuleEntity.getMenus();
            for (SystemMenuEntity systemMenuEntity : systemMenuEntities) {
                menus.add(ObjectUtils.convert(systemMenuEntity, SystemMenuDTO.class));
            }

            List<ModuleAccountInfoEntity> moduleAccountInfoEntities = systemUserModuleEntity.getAccounts();
            for (ModuleAccountInfoEntity moduleAccountInfoEntity : moduleAccountInfoEntities) {
                accounts.add(ObjectUtils.convert(moduleAccountInfoEntity, ModuleAccountInfoDTO.class));
            }

            systemUserModuleDTO.setMenus(menus);
            systemUserModuleDTO.setAccounts(accounts);

            systemUserModules.add(systemUserModuleDTO);
        }

        systemUserDTO.setSystemUserModules(systemUserModules);

        return systemUserDTO;
    }


    public static List<ModuleAccountInfoDTO> findAccountsBySystemName(SystemUserDTO systemUserDTO, String systemName) {
        final List<ModuleAccountInfoDTO> moduleAccountInfoDTOs = Lists.newArrayList();
        systemUserDTO.getSystemUserModules().stream().filter((systemUserModuleDTO -> systemUserModuleDTO.getModuleName().equals(systemName))).findFirst().ifPresent(systemUserModuleDTO1 -> {
            moduleAccountInfoDTOs.addAll(systemUserModuleDTO1.getAccounts());
        });

        return moduleAccountInfoDTOs;
    }

    public static void consumeCurrentSystemAccount(SystemUserDTO systemUserDTO, String systemName, Consumer<SystemUserModuleDTO> consumer) {
        systemUserDTO.getSystemUserModules().stream().filter((systemUserModuleDTO -> systemUserModuleDTO.getModuleName().equals(systemName))).findFirst().ifPresent(consumer);
    }
}
