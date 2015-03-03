package com.perfect.service.impl;

import com.perfect.dao.creative.SublinkDAO;
import com.perfect.dto.creative.SublinkDTO;
import com.perfect.service.SublinkService;
import com.perfect.utils.paging.PagerInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by XiaoWei on 2015/2/25.
 */
@Service
public class SublinkServiceImpl implements SublinkService {
    @Resource
    private SublinkDAO sublinkDAO;
    @Override
    public String customSave(SublinkDTO dto) {
        if(dto.getAdgroupId()!=null){
            SublinkDTO sublinkDTOLong=sublinkDAO.findByAdgroupLongId(dto.getAdgroupId());
            if(sublinkDTOLong==null){
                 sublinkDAO.customSave(dto);
                return "1";
            }
        }
        if(dto.getAdgroupObjId()!=null){
            SublinkDTO sublinkDTOObj=sublinkDAO.findByAdgroupObjId(dto.getAdgroupObjId());
            if(sublinkDTOObj==null){
                sublinkDAO.customSave(dto);
                return "1";
            }
        }
        return null;
    }

    @Override
    public PagerInfo findByParams(Map<String, Object> maps,int nowPage,int pageSize) {
        return sublinkDAO.findByParams(maps,nowPage,pageSize);
    }

    @Override
    public PagerInfo findByPagerInfo(Long l, int nowPage, int pageSize) {
        return sublinkDAO.findByPagerInfo(l,nowPage,pageSize);
    }

    @Override
    public PagerInfo findByPagerInfoForLongs(List<Long> ls, int nowPage, int pageSize) {
        return sublinkDAO.findByPagerInfoForLongs(ls,nowPage,pageSize);
    }

    @Override
    public PagerInfo findByPagerInfo(Map<String, Object> map, int nowPage, int pageSize) {
        return sublinkDAO.findByPagerInfo(map,nowPage,pageSize);
    }

}
