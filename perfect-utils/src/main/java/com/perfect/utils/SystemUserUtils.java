package com.perfect.utils;

import com.perfect.dto.sys.SystemUserDTO;
import com.perfect.entity.sys.SystemUserEntity;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by dolphineor on 15-12-18.
 */
public class SystemUserUtils {

    public static SystemUserEntity convertToEntity(SystemUserDTO dto) {
        SystemUserEntity systemUserEntity = new SystemUserEntity();
        Class<SystemUserEntity> entityClazz = SystemUserEntity.class;
        Class<SystemUserDTO> dtoClazz = SystemUserDTO.class;

        Map<String, Field> entityFieldMap = Arrays.stream(entityClazz.getDeclaredFields()).collect(Collectors.toMap(Field::getName, field -> field));
        Field dtoFields[] = dtoClazz.getDeclaredFields();

        for (Field field : dtoFields) {
            if (entityFieldMap.containsKey(field.getName())) {
                try {
                    Object value = PropertyUtils.getReadMethod(new PropertyDescriptor(field.getName(), dtoClazz)).invoke(dto);
                    PropertyUtils.getWriteMethod(new PropertyDescriptor(field.getName(), entityClazz)).invoke(systemUserEntity, PropertyUtils.getPropertyType(dto, field.getName()).cast(value));
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return systemUserEntity;
    }

    public static SystemUserDTO convertToDTO(SystemUserEntity entity) {
        SystemUserDTO systemUserDTO = new SystemUserDTO();
        Class<SystemUserEntity> entityClazz = SystemUserEntity.class;
        Class<SystemUserDTO> dtoClazz = SystemUserDTO.class;

        Map<String, Field> dtoFieldMap = Arrays.stream(dtoClazz.getDeclaredFields()).collect(Collectors.toMap(Field::getName, field -> field));
        Field dtoFields[] = entityClazz.getDeclaredFields();

        for (Field field : dtoFields) {
            if (dtoFieldMap.containsKey(field.getName())) {
                try {
                    Object value = PropertyUtils.getReadMethod(new PropertyDescriptor(field.getName(), dtoClazz)).invoke(entity);
                    PropertyUtils.getWriteMethod(new PropertyDescriptor(field.getName(), entityClazz)).invoke(systemUserDTO, PropertyUtils.getPropertyType(entity, field.getName()).cast(value));
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return systemUserDTO;
    }
}
