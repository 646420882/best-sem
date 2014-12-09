package com.perfect.service.impl;

import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.dao.SourceDAO;
import com.perfect.dto.count.CensusDTO;
import com.perfect.dto.count.SourcesAllDTO;
import com.perfect.service.SourceService;
import com.perfect.vo.BasedDataVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
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
     *
     * @param dates      查询时间范围
     * @param accessType 访问类型  0：全部     1：计算机    2：移动设备
     * @param userType   访问用户类型  0：全部   1：新访客    2：老访客
     * @return
     */
    public List<CensusDTO> getSourceAnalysis(List<Date> dates, Integer accessType, Integer userType) {

        List<CensusDTO> censusDTOs = sourceDao.getSourceAnalysis(dates, accessType, userType);
        DecimalFormat df = new DecimalFormat("#.00");
        Map<String, String> map = new HashMap<>();
        //计算全部来源头数据
        Map<String, String> headMap = new HashMap<>();
        //--------------------计算全部来源头数据-----------------------------
        headMap.put("headPv", censusDTOs.size() + "");
        //计算新访客比例
        double newUserNumber = 0.0;
        for (CensusDTO censusDTO : censusDTOs) {
            if (censusDTO.getUserType() == 1) newUserNumber++;
        }
        double ds = newUserNumber / censusDTOs.size() * 100;
        headMap.put("headXFK", df.format(ds));
        //跳出率
        headMap.put("headTCL", "--");
        //订单数
        headMap.put("headDDS", "--");
        //订单金额
        headMap.put("headDDJE", "--");
        //订单转化率
        headMap.put("headDDZHL", "--");
        //--------------------计算全部来源头数据----END-------------------------
        //--------------------分类统计-----------------------------------------
        Map<String, String> tableMap = new HashMap<>();

        SourcesAllDTO sourcesAllDTO = sourceDao.getGroupFind(dates, accessType, userType);

        //处理外部链接数据
        for (BasedDataVO basedDataVO : sourcesAllDTO.getIntoPageData()) {
            sourceDao.getDesignationData(dates, accessType, userType, MongoEntityConstants.INTOPAGE,basedDataVO.getTp());
            //<-------------------------dsfsdfkjmhsdlkflkjdsajflkdsalkfjdsa----------------------------------->
        }


        return censusDTOs;
    }
}
