package com.perfect.db.mongodb.impl;

import com.perfect.dao.sys.SystemUserDAO;
import com.perfect.db.mongodb.base.AbstractSysBaseDAOImpl;
import com.perfect.dto.SystemUserDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.entity.sys.BaiduAccountInfoEntity;
import com.perfect.entity.sys.SystemUserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by vbzer_000 on 2014-6-19.
 * 2014-12-2 refactor
 */
@Repository("systemUserDAO")
public class SystemUserDAOImpl extends AbstractSysBaseDAOImpl<SystemUserDTO, String> implements SystemUserDAO {


    private Logger logger = LoggerFactory.getLogger(SystemUserDAOImpl.class);


    @Override
    @SuppressWarnings("unchecked")
    public Class<SystemUserEntity> getEntityClass() {
        return SystemUserEntity.class;
    }

    @Override
    public Class<SystemUserDTO> getDTOClass() {
        return SystemUserDTO.class;
    }

    @Override
    public void addBaiduAccount(List<BaiduAccountInfoDTO> list, String currSystemUserName) {
        SystemUserDTO currSystemUserDTO = findByUserName(currSystemUserName);
        List<BaiduAccountInfoDTO> _list = currSystemUserDTO.getBaiduAccountInfoDTOs();
        if (_list == null) {
            _list = new ArrayList<>();
        }
        _list.addAll(list);
        getSysMongoTemplate().updateFirst(Query.query(Criteria.where("userName").is(currSystemUserName)), Update.update("baiduAccountInfos", _list), "sys_user");
    }

    @Override
    public SystemUserDTO findByAid(long aid) {
        Query query = Query.query(Criteria.where("bdAccounts._id").is(aid));
        SystemUserDTO systemUserDTO = new SystemUserDTO();
        BeanUtils.copyProperties(getSysMongoTemplate().findOne(query, getEntityClass()), systemUserDTO);
        return systemUserDTO;
    }

    @Override
    public void insertAccountInfo(String user, BaiduAccountInfoDTO baiduAccountInfoDTO) {

        SystemUserDTO systemUserDTO = findByUserName(user);
        if (systemUserDTO.getBaiduAccountInfoDTOs().isEmpty())
            baiduAccountInfoDTO.setDfault(true);

        BaiduAccountInfoEntity baiduAccountInfoEntity = new BaiduAccountInfoEntity();
        BeanUtils.copyProperties(baiduAccountInfoDTO, baiduAccountInfoEntity);
        Update update = new Update();
        update.addToSet("bdAccounts", baiduAccountInfoEntity);
        getSysMongoTemplate().upsert(Query.query(Criteria.where("userName").is(user)), update, getEntityClass());
    }

    @Override
    public void removeAccountInfo(Long id) {
        Update update = new Update();
        update.unset("bdAccounts");
        getSysMongoTemplate().updateFirst(Query.query(Criteria.where("bdAccounts._id").is(id)), update, getEntityClass());
    }

    @Override
    public void initAccount(SystemUserDTO systemUserDTO) {
        List<BaiduAccountInfoDTO> baiduAccountInfoDTOList = systemUserDTO.getBaiduAccountInfoDTOs();

        if (baiduAccountInfoDTOList == null || baiduAccountInfoDTOList.isEmpty()) {
            logger.warn("账号未绑定百度推广账户");
            return;
        }

        String userName = systemUserDTO.getUserName();


    }

    @Override
    public SystemUserDTO findByUserName(String userName) {
        SystemUserDTO user = new SystemUserDTO();
        BeanUtils.copyProperties(
                getSysMongoTemplate().findOne(
                        Query.query(Criteria.where("userName").is(userName)),
                        getEntityClass(),
                        "sys_user"),
                user);
        return user;
    }

    @Override
    public SystemUserDTO save(SystemUserDTO dto) {
        SystemUserEntity systemUserEntity = new SystemUserEntity();
        BeanUtils.copyProperties(dto, systemUserEntity);
        getSysMongoTemplate().save(systemUserEntity);
        BeanUtils.copyProperties(systemUserEntity, dto);
        return dto;
    }

    @Override
    public List<SystemUserDTO> find(Map<String, Object> params, int skip, int limit) {
        return null;
    }

}
