package com.perfect.service.impl;

import com.perfect.dao.RegionalCodeDAO;
import com.perfect.dto.RedisRegionalDTO;
import com.perfect.dto.RegionalCodeDTO;
import com.perfect.service.SysRegionalService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SubDong on 2014/9/29.
 */
@Component("sysRegionalService")
public class SysRegionalServiceImpl implements SysRegionalService {

    @Resource
    private RegionalCodeDAO regionalCodeDAO;

    @Override
    public Map<String, List<RedisRegionalDTO>> regionalProvinceId(List<Integer> listId) {
        String fideName = "provinceId";
        Map<String, List<RedisRegionalDTO>> listMap = new HashMap<>();
        for (Integer integer : listId) {
            if (integer >= 1000) {
                String peroId = String.valueOf(integer / 1000);
                List<RegionalCodeDTO> regionals = regionalCodeDAO.getRegional(fideName, peroId);
                List<RedisRegionalDTO> dtos = new ArrayList<>();
                if (regionals.size() == 0) {
                    RedisRegionalDTO regionalDTO = new RedisRegionalDTO();
                    regionalDTO.setRegionalId("-1");
                    regionalDTO.setRegionalName(String.valueOf(integer));
                    dtos.add(regionalDTO);
                } else {
                    for (RegionalCodeDTO codeDTO : regionals) {
                        if (!String.valueOf(integer).equals(codeDTO.getProvinceId() + codeDTO.getRegionId())) {
                            RedisRegionalDTO regionalDTO = new RedisRegionalDTO();
                            regionalDTO.setRegionalId(codeDTO.getProvinceId() + codeDTO.getRegionId());
                            regionalDTO.setRegionalName(codeDTO.getRegionName());
                            dtos.add(regionalDTO);
                        }
                    }
                }

                listMap.put(String.valueOf(integer), dtos);
            } else {
                List<RegionalCodeDTO> regionals = regionalCodeDAO.getRegional(fideName, String.valueOf(integer));
                List<RedisRegionalDTO> dtos = new ArrayList<>();
                if (regionals.size() == 0) {
                    RedisRegionalDTO regionalDTO = new RedisRegionalDTO();
                    regionalDTO.setRegionalId("-1");
                    regionalDTO.setRegionalName(String.valueOf(integer));
                    dtos.add(regionalDTO);
                } else {
                    for (RegionalCodeDTO codeDTO : regionals) {
                        if (!String.valueOf(integer * 1000).equals(codeDTO.getProvinceId() + codeDTO.getRegionId())) {
                            RedisRegionalDTO regionalDTO = new RedisRegionalDTO();
                            regionalDTO.setRegionalId(codeDTO.getProvinceId() + codeDTO.getRegionId());
                            regionalDTO.setRegionalName(codeDTO.getRegionName());
                            dtos.add(regionalDTO);
                        }
                    }
                }
                listMap.put(String.valueOf(integer * 1000), dtos);
            }
        }
        return listMap;
    }

    @Override
    public Map<String, List<RedisRegionalDTO>> regionalProvinceName(List<String> listId) {
        String fideName = "provinceName";
        Map<String, List<RedisRegionalDTO>> listMap = new HashMap<>();
        for (String integer : listId) {
            List<RegionalCodeDTO> regionals = regionalCodeDAO.getRegional(fideName, integer);
            List<RedisRegionalDTO> dtos = new ArrayList<>();
            if (regionals.size() == 0) {
                RedisRegionalDTO regionalDTO = new RedisRegionalDTO();
                regionalDTO.setRegionalId("-1");
                regionalDTO.setRegionalName(String.valueOf(integer));
                dtos.add(regionalDTO);
            } else {
                for (RegionalCodeDTO codeDTO : regionals) {
                    if (!String.valueOf(integer).equals(codeDTO.getProvinceId() + codeDTO.getRegionId())) {
                        RedisRegionalDTO regionalDTO = new RedisRegionalDTO();
                        regionalDTO.setRegionalId(codeDTO.getProvinceId() + codeDTO.getRegionId());
                        if ("".equals(codeDTO.getRegionName())) {
                            regionalDTO.setRegionalName(integer);
                        } else {
                            regionalDTO.setRegionalName(codeDTO.getRegionName());
                        }

                        dtos.add(regionalDTO);
                    }
                }
            }
            listMap.put(integer, dtos);
        }
        return listMap;
    }

    @Override
    public Map<String, List<Object>> getProvince() {
        String fideName = "regionId";
        String id = "000";
        List<RegionalCodeDTO> regionals = regionalCodeDAO.getRegional(fideName, id);
        Map<String, List<Object>> returnMap = new HashMap<>();
        List<Object> dtos = new ArrayList<>();
        for (RegionalCodeDTO codeDTO : regionals) {
            if (!"00".equals(codeDTO.getProvinceId())) {
                RedisRegionalDTO regionalDTO = new RedisRegionalDTO();
                Map<String, String> map = new HashMap<>();
                map.put("value", codeDTO.getProvinceId());
                map.put("name", codeDTO.getProvinceName());
                dtos.add(map);
            }
        }
        returnMap.put("rows", dtos);

        return returnMap;
    }

    @Override
    public String getProvinceNameById(Integer provinceId) {
        String fieldName = "provinceId";

        return getNameById(fieldName, provinceId);
    }

    @Override
    public String getRegionNameById(Integer regionId) {
        String fieldName = "regionId";

        return getNameById(fieldName, regionId);
    }

    private String getNameById(String fieldName, Integer id) {

        List<RegionalCodeDTO> list = regionalCodeDAO.getRegional(fieldName, id.toString());
        if (list == null || list.isEmpty())
            return null;
        else
            return list.get(0).getProvinceName();
    }

    @Override
    public List<RegionalCodeDTO> getRegionalName(List<String> proName) {
        String fideName = "regionName";
        List<RegionalCodeDTO> dtos = new ArrayList<>();
        for (String dto : proName) {
            List<RegionalCodeDTO> regionals = regionalCodeDAO.getRegional(fideName, dto);
            if (regionals.size() == 0) {
                String fideName1 = "provinceName";
                List<RegionalCodeDTO> regionals1 = regionalCodeDAO.getRegional(fideName1, dto);
                if (regionals1.size() == 0) {
                    RegionalCodeDTO regionalCodeDTO = new RegionalCodeDTO();
                    regionalCodeDTO.setRegionId("-1");
                    regionalCodeDTO.setRegionName(dto);
                    dtos.add(regionalCodeDTO);
                } else {
                    for (RegionalCodeDTO codeDTO1 : regionals1) {
                        if (!codeDTO1.getRegionId().equals("999")) {
                            RegionalCodeDTO regionalCodeDTO = new RegionalCodeDTO();
                            regionalCodeDTO.setProvinceId(codeDTO1.getProvinceId() + "000");
                            regionalCodeDTO.setProvinceName(codeDTO1.getProvinceName());
                            regionalCodeDTO.setRegionId(codeDTO1.getProvinceId() + codeDTO1.getRegionId());
                            regionalCodeDTO.setRegionName(codeDTO1.getRegionName());
                            regionalCodeDTO.setStateId(codeDTO1.getStateId() + "00000");
                            regionalCodeDTO.setStateName(codeDTO1.getStateName());
                            dtos.add(regionalCodeDTO);
                        } else {
                            RegionalCodeDTO regionalCodeDTO = new RegionalCodeDTO();
                            regionalCodeDTO.setProvinceId("");
                            regionalCodeDTO.setProvinceName("");
                            regionalCodeDTO.setRegionId("");
                            regionalCodeDTO.setRegionName("");
                            regionalCodeDTO.setStateId(codeDTO1.getStateId() + codeDTO1.getProvinceId() + codeDTO1.getRegionId());
                            regionalCodeDTO.setStateName(codeDTO1.getRegionName());
                            dtos.add(regionalCodeDTO);
                        }

                    }
                }
            } else {
                for (RegionalCodeDTO codeDTO : regionals) {
                    if (!codeDTO.getRegionId().equals("999")) {
                        RegionalCodeDTO regionalCodeDTO = new RegionalCodeDTO();
                        regionalCodeDTO.setProvinceId(codeDTO.getProvinceId() + "000");
                        regionalCodeDTO.setProvinceName(codeDTO.getProvinceName());
                        regionalCodeDTO.setRegionId(codeDTO.getProvinceId() + codeDTO.getRegionId());
                        regionalCodeDTO.setRegionName(codeDTO.getRegionName());
                        regionalCodeDTO.setStateId(codeDTO.getStateId() + "00000");
                        regionalCodeDTO.setStateName(codeDTO.getStateName());
                        dtos.add(regionalCodeDTO);
                    } else {
                        RegionalCodeDTO regionalCodeDTO = new RegionalCodeDTO();
                        regionalCodeDTO.setProvinceId("");
                        regionalCodeDTO.setProvinceName("");
                        regionalCodeDTO.setRegionId("");
                        regionalCodeDTO.setRegionName("");
                        regionalCodeDTO.setStateId(codeDTO.getStateId() + codeDTO.getProvinceId() + codeDTO.getRegionId());
                        regionalCodeDTO.setStateName(codeDTO.getRegionName());
                        dtos.add(regionalCodeDTO);
                    }

                }
            }

        }
        return dtos;
    }

    @Override
    public List<RegionalCodeDTO> getRegionalId(List<Integer> listId) {
        String fideName = "regionId";
        List<RegionalCodeDTO> dtos = new ArrayList<>();
        for (Integer dto : listId) {
            List<RegionalCodeDTO> regionals = new ArrayList<>();
            if (dto < 1000) {
                String regionid = "";
                if (dto < 10) {
                    regionid = "00" + dto;
                } else if (dto < 100) {
                    regionid = "0" + dto;
                }
                regionals = regionalCodeDAO.getRegional(fideName, "".equals(regionid) ? dto + "" : String.valueOf(dto));
            } else if (dto < 100000) {
                if (dto % 1000 != 0) {
                    String regionid = "";
                    int i = dto % 1000;
                    if (i < 10) {
                        regionid = "00" + i;
                    } else if (i < 100) {
                        regionid = "0" + i;
                    } else {
                        regionid = i + "";
                    }
                    regionals = regionalCodeDAO.getRegional(fideName, "".equals(regionid) ? i + "" : String.valueOf(regionid));
                }
            } else if (dto >= 100000 && dto <= 10000000) {
                if (dto % 1000 != 0) {
                    String regionid = "";
                    int i = dto % 1000;
                    if (i < 10) {
                        regionid = "00" + i;
                    } else if (i < 100) {
                        regionid = "0" + i;
                    }
                    regionals = regionalCodeDAO.getRegional(fideName, "".equals(regionid) ? i + "" : String.valueOf(regionid));
                }
            } else {
                regionals = new ArrayList<>();
            }

            if (regionals.size() == 0) {
                String fideName1 = "provinceId";
                int i = dto / 1000;
                List<RegionalCodeDTO> regionals1 = regionalCodeDAO.getRegional(fideName1, String.valueOf(i));
                if (regionals1.size() == 0) {
                    RegionalCodeDTO regionalCodeDTO = new RegionalCodeDTO();
                    regionalCodeDTO.setRegionId("-1");
                    regionalCodeDTO.setRegionName(String.valueOf(dto));
                    dtos.add(regionalCodeDTO);
                } else {
                    for (RegionalCodeDTO codeDTO1 : regionals1) {
                        if (!codeDTO1.getRegionId().equals("999")) {
                            RegionalCodeDTO regionalCodeDTO = new RegionalCodeDTO();
                            regionalCodeDTO.setProvinceId(codeDTO1.getProvinceId() + "000");
                            regionalCodeDTO.setProvinceName(codeDTO1.getProvinceName());
                            regionalCodeDTO.setRegionId(codeDTO1.getProvinceId() + codeDTO1.getRegionId());
                            regionalCodeDTO.setRegionName(codeDTO1.getRegionName());
                            regionalCodeDTO.setStateId(codeDTO1.getStateId() + "00000");
                            regionalCodeDTO.setStateName(codeDTO1.getStateName());
                            dtos.add(regionalCodeDTO);
                        } else {
                            RegionalCodeDTO regionalCodeDTO = new RegionalCodeDTO();
                            regionalCodeDTO.setProvinceId("");
                            regionalCodeDTO.setProvinceName("");
                            regionalCodeDTO.setRegionId("");
                            regionalCodeDTO.setRegionName("");
                            regionalCodeDTO.setStateId(codeDTO1.getStateId() + codeDTO1.getProvinceId() + codeDTO1.getRegionId());
                            regionalCodeDTO.setStateName(codeDTO1.getRegionName());
                            dtos.add(regionalCodeDTO);
                        }

                    }
                }
            } else {
                for (RegionalCodeDTO codeDTO : regionals) {
                    if (!codeDTO.getRegionId().equals("999")) {
                        RegionalCodeDTO regionalCodeDTO = new RegionalCodeDTO();
                        regionalCodeDTO.setProvinceId(codeDTO.getProvinceId() + "000");
                        regionalCodeDTO.setProvinceName(codeDTO.getProvinceName());
                        regionalCodeDTO.setRegionId(codeDTO.getProvinceId() + codeDTO.getRegionId());
                        regionalCodeDTO.setRegionName(codeDTO.getRegionName());
                        regionalCodeDTO.setStateId(codeDTO.getStateId() + "00000");
                        regionalCodeDTO.setStateName(codeDTO.getStateName());
                        dtos.add(regionalCodeDTO);
                    } else {
                        RegionalCodeDTO regionalCodeDTO = new RegionalCodeDTO();
                        regionalCodeDTO.setProvinceId("");
                        regionalCodeDTO.setProvinceName("");
                        regionalCodeDTO.setRegionId("");
                        regionalCodeDTO.setRegionName("");
                        regionalCodeDTO.setStateId(codeDTO.getStateId() + codeDTO.getProvinceId() + codeDTO.getRegionId());
                        regionalCodeDTO.setStateName(codeDTO.getRegionName());
                        dtos.add(regionalCodeDTO);
                    }
                }
            }
        }
        return dtos;
    }


    public List<RegionalCodeDTO> getRegionalByRegionalId(List<Integer> listId) {
        List<RegionalCodeDTO> dtos = new ArrayList<>();

        String fideName = "provinceId";
        for (Integer dto : listId) {
            dto = dto / 1000 % 100;
            dtos.add(regionalCodeDAO.getRegionalByRegionId(fideName, dto + ""));
        }
        return dtos;
    }


    /******************************备份Start**********************************************/
    /*@Override
    public List<RegionalCodeDTO> getRegionalId(List<Integer> listId) {
        String fideName = "regionId";
        List<RegionalCodeDTO> dtos = new ArrayList<>();
        for(Integer dto:listId){
            List<RegionalCodeDTO> regionals = new ArrayList<>();
            if(dto < 1000){
                String regionid = "";
                if(dto<10){
                    regionid = "00"+dto;
                }else if(dto<100){
                    regionid = "0"+dto;
                }
                regionals = regionalCodeDAO.getRegional(fideName, String.valueOf(regionid));
            }else if(dto < 100000){
                String regionid = "";
                int i = dto%1000;
                if(i<10){
                    regionid = "00"+i;
                }else if(i<100){
                    regionid = "0"+i;
                }
                regionals = regionalCodeDAO.getRegional(fideName, String.valueOf(regionid));
            }else if(dto >= 100000 && dto <= 10000000 ){
                String regionid = "";
                int i = dto%1000;
                if(i<10){
                    regionid = "00"+i;
                }else if(i<100){
                    regionid = "0"+i;
                }
                regionals = regionalCodeDAO.getRegional(fideName, String.valueOf(regionid));
            }else{
                regionals = new ArrayList<>();
            }

            if(regionals.size() == 0){

                RegionalCodeDTO regionalCodeDTO = new RegionalCodeDTO();
                regionalCodeDTO.setRegionId("-1");
                regionalCodeDTO.setRegionName(String.valueOf(dto));
                dtos.add(regionalCodeDTO);
            }else{
                for(RegionalCodeDTO codeDTO:regionals){
                    if(!codeDTO.getRegionId().equals("999")){
                        RegionalCodeDTO regionalCodeDTO = new RegionalCodeDTO();
                        regionalCodeDTO.setProvinceId(codeDTO.getProvinceId()+"000");
                        regionalCodeDTO.setProvinceName(codeDTO.getProvinceName());
                        regionalCodeDTO.setRegionId(codeDTO.getProvinceId()+codeDTO.getRegionId());
                        regionalCodeDTO.setRegionName(codeDTO.getRegionName());
                        regionalCodeDTO.setStateId(codeDTO.getStateId()+"00000");
                        regionalCodeDTO.setStateName(codeDTO.getStateName());
                        dtos.add(regionalCodeDTO);
                    }else{
                        RegionalCodeDTO regionalCodeDTO = new RegionalCodeDTO();
                        regionalCodeDTO.setProvinceId("");
                        regionalCodeDTO.setProvinceName("");
                        regionalCodeDTO.setRegionId("");
                        regionalCodeDTO.setRegionName("");
                        regionalCodeDTO.setStateId(codeDTO.getStateId()+codeDTO.getProvinceId()+codeDTO.getRegionId());
                        regionalCodeDTO.setStateName(codeDTO.getRegionName());
                        dtos.add(regionalCodeDTO);
                    }
                }
            }
        }
        return dtos;
    }*/
   /* @Override
    public List<RegionalCodeDTO> getRegionalName(List<String> proName) {
        String fideName = "regionName";
        List<RegionalCodeDTO> dtos = new ArrayList<>();
        for(String dto:proName){
            List<RegionalCodeDTO> regionals = regionalCodeDAO.getRegional(fideName,dto);
            if(regionals.size() == 0){
                RegionalCodeDTO regionalCodeDTO = new RegionalCodeDTO();
                regionalCodeDTO.setRegionId("-1");
                regionalCodeDTO.setRegionName(dto);
                dtos.add(regionalCodeDTO);
            }else{
                for(RegionalCodeDTO codeDTO:regionals){
                    if(!codeDTO.getRegionId().equals("999")){
                        RegionalCodeDTO regionalCodeDTO = new RegionalCodeDTO();
                        regionalCodeDTO.setProvinceId(codeDTO.getProvinceId()+"000");
                        regionalCodeDTO.setProvinceName(codeDTO.getProvinceName());
                        regionalCodeDTO.setRegionId(codeDTO.getProvinceId()+codeDTO.getRegionId());
                        regionalCodeDTO.setRegionName(codeDTO.getRegionName());
                        regionalCodeDTO.setStateId(codeDTO.getStateId()+"00000");
                        regionalCodeDTO.setStateName(codeDTO.getStateName());
                        dtos.add(regionalCodeDTO);
                    }else{
                        RegionalCodeDTO regionalCodeDTO = new RegionalCodeDTO();
                        regionalCodeDTO.setProvinceId("");
                        regionalCodeDTO.setProvinceName("");
                        regionalCodeDTO.setRegionId("");
                        regionalCodeDTO.setRegionName("");
                        regionalCodeDTO.setStateId(codeDTO.getStateId()+codeDTO.getProvinceId()+codeDTO.getRegionId());
                        regionalCodeDTO.setStateName(codeDTO.getRegionName());
                        dtos.add(regionalCodeDTO);
                    }

                }
            }

        }
        return dtos;
    }*/

    /******************************备份end**********************************************/
}
