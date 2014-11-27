package com.perfect.service.impl;

import com.perfect.dao.CreativeBackUpDAO;
import com.perfect.dao.CreativeDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.dto.backup.CreativeBackUpDTO;
import com.perfect.dto.creative.CreativeDTO;
import com.perfect.entity.CreativeEntity;
import com.perfect.entity.backup.CreativeBackUpEntity;
import com.perfect.service.CreativeBackUpService;
import com.perfect.utils.Pager;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/9/4.
 * 2014-11-26 refactor
 */
@Service("creativeBackUpService")
public class CreativeBacUpkServiceServiceImpl extends AbstractUserBaseDAOImpl<CreativeBackUpDTO, Long> implements CreativeBackUpService {
    @Resource
    private CreativeBackUpDAO creativeBackUpDAO;
    @Resource
    private CreativeDAO creativeDAO;

    @Override
    public Class<CreativeBackUpDTO> getEntityClass() {
        return null;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }

    @Override
    public CreativeBackUpDTO findByStringId(String id) {
        return creativeBackUpDAO.findByStringId(id);
    }

    @Override
    public CreativeBackUpDTO findByLongId(Long crid) {
        return creativeBackUpDAO.findByLongId(crid);
    }

    @Override
    public void deleteByLongId(Long crid) {
        creativeBackUpDAO.deleteByLongId(crid);
    }

    @Override
    public CreativeBackUpDTO reBack(Long crid) {
        CreativeBackUpDTO creativeBackUpDTOFind = creativeBackUpDAO.findByLongId(crid);
        if (creativeBackUpDTOFind != null) {
            creativeBackUpDTOFind.setLocalStatus(null);
            CreativeDTO creativeDTO = new CreativeDTO();
            BeanUtils.copyProperties(creativeBackUpDTOFind, creativeDTO);
            creativeDAO.insertByReBack(creativeDTO);
            creativeBackUpDAO.deleteByLongId(crid);
        }
        return creativeBackUpDTOFind;
    }
}
