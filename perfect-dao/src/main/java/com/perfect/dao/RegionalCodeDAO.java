package com.perfect.dao;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.regional.RegionalCodeDTO;

import java.util.List;

/**
 * Created by SubDong on 2014/9/29.
 */
public interface RegionalCodeDAO extends HeyCrudRepository<RegionalCodeDTO,Long> {
    /**
     * 添加地域代码
     *
     * @param redisList
     */
    public void insertRegionalCode(List<RegionalCodeDTO> redisList);

    /**
     * 查询多条
     *
     * @param fieldName
     * @param id
     * @return
     */
    public List<RegionalCodeDTO> getRegional(String fieldName, String id);


    /**
     * 根据一个省id得到一个省的信息
     *
     * @param id
     * @return
     */
    public RegionalCodeDTO getRegionalByRegionId(String feidName, String id);
}
