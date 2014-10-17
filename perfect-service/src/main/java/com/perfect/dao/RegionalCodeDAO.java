package com.perfect.dao;

import com.perfect.dto.RegionalCodeDTO;

import java.util.List;

/**
 * Created by SubDong on 2014/9/29.
 */
public interface RegionalCodeDAO {
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
