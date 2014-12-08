package com.perfect.service;

import com.perfect.dto.regional.RegionalCodeDTO;
import com.perfect.dto.regional.RegionalReturnDataDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by SubDong on 2014/9/29.
 */
public interface SysRegionalService {

    /**
     * 通过省域ID获取对应的区域
     * 如未查询到对应的区域ID 则 区域ID返回-1
     *
     * @param listId
     * @return
     */
    public Map<String, List<RegionalReturnDataDTO>> regionalProvinceId(List<Integer> listId);

    /**
     * 通过省域名称查询对应的所有区域
     * 如未查询到对应的区域名称 则 区域ID返回-1
     *
     * @param listId
     * @return
     */
    public Map<String, List<RegionalReturnDataDTO>> regionalProvinceName(List<String> listId);

    /**
     * 获取所有省份
     *
     * @return
     */
    public Map<String, List<RegionalReturnDataDTO>> getProvince();

    /**
     * 通过省级ID查询省级信息
     * @param provinceId
     * @return
     */
    public String getProvinceNameById(Integer provinceId);

    /**
     * 通过区域ID  获取区域信息
     * @param regionId
     * @return
     */
    public String getRegionNameById(Integer regionId);

    /**
     * 通过区域名称获得 相应的区域id 以及省份 id 及名称
     * 如未查询到对应的区域名称 则 区域ID返回-1
     *
     * @param proName
     * @return
     */
    public List<RegionalCodeDTO> getRegionalName(List<String> proName);

    /**
     * 通过区域ID查询 区域名称 及省份id 省份名称
     * 如未查询到对应的区域ID 则 区域ID返回-1
     *
     * @param listId
     * @return
     */
    public List<RegionalCodeDTO> getRegionalId(List<Integer> listId);


    /**
     * 通过多个省id得到该省的信息
     *
     * @param listId
     * @return
     */
    public List<RegionalCodeDTO> getProvinceIdByRegionalId(List<Integer> listId);

    /**
     * 通过地域名称查询省级和区域id
     * @param regionalName
     * @return
     */
    public Map<Integer,String> getRegionalByRegionName(List<String> regionalName);
}
