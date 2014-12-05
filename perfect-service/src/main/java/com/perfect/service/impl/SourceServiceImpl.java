package com.perfect.service.impl;

import com.perfect.dao.SourceDAO;
import com.perfect.dto.count.CensusDTO;
import com.perfect.service.SourceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SubDong on 2014/12/1.
 */
@Service("sourceService")
public class SourceServiceImpl implements SourceService {

    @Resource
    SourceDAO sourceDao;
    /**
     * 得到 来源分析 基础数据
     * @param dates 查询时间范围
     * @param findType 查询的类型  0：全部来源  1：搜索引擎  2：搜索词   3：外部链接
     * @param accessType 访问类型  0：全部     1：计算机    2：移动设备
     * @param userType 访问用户类型  0：全部   1：新访客    2：老访客
     * @return
     */
    public List<CensusDTO> getSourceAnalysis(List<Date> dates,int findType,Integer accessType,Integer userType){

        List<CensusDTO> censusDTOs = sourceDao.getSourceAnalysis(dates,accessType,userType);

        switch (findType){
            case 0:
                Map<String,String> map = new HashMap<>();
                //计算全部来源头数据
                map.put("headPv",censusDTOs.size()+"");




                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }

        return censusDTOs;
    }
}
