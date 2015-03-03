package com.perfect.service.impl;

import com.perfect.dao.creative.MobileSublinkDAO;
import com.perfect.dto.creative.MobileSublinkDTO;
import com.perfect.service.MobileSublinkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by XiaoWei on 2015/2/27.
 */
@Service
public class MobileSublinkServiceImpl implements MobileSublinkService {
    @Resource
    private MobileSublinkDAO mobileSublinkDAO;
    @Override
    public String customSave(MobileSublinkDTO dto) {
        if(dto.getAdgroupId()!=null){
            MobileSublinkDTO mobileSublinkDTOLong=mobileSublinkDAO.findByAdgroupLongId(dto.getAdgroupId());
            if(mobileSublinkDTOLong==null){
                mobileSublinkDAO.customSave(dto);
                return "1";
            }
        }
        if(dto.getAdgroupObjId()!=null){
            MobileSublinkDTO mobileSublinkDTOString=mobileSublinkDAO.findByAdgroupObjId(dto.getAdgroupObjId());
            if(mobileSublinkDTOString==null){
                mobileSublinkDAO.customSave(dto);
                return "1";
            }
        }
        return null;
    }

}
