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
public class SysRegionalServiceImpl implements SysRegionalService{

    @Resource
    private RegionalCodeDAO regionalCodeDAO;

    @Override
    public Map<String, List<RedisRegionalDTO>> regionalProvinceId(List<Integer> listId) {
        String fideName="provinceId";
        Map<String,List<RedisRegionalDTO>> listMap = new HashMap<>();
        for(Integer integer : listId){
            if(integer >= 1000){
                String peroId = String.valueOf(integer / 1000);
                List<RegionalCodeDTO> regionals = regionalCodeDAO.getRegional(fideName,peroId);
                List<RedisRegionalDTO> dtos = new ArrayList<>();
                if(regionals.size() == 0){
                    RedisRegionalDTO regionalDTO = new RedisRegionalDTO();
                    regionalDTO.setRegionalId("-1");
                    regionalDTO.setRegionalName(String.valueOf(integer));
                    dtos.add(regionalDTO);
                }else{
                    for (RegionalCodeDTO codeDTO:regionals){
                        if(!String.valueOf(integer).equals(codeDTO.getProvinceId()+codeDTO.getRegionId())){
                            RedisRegionalDTO regionalDTO = new RedisRegionalDTO();
                            regionalDTO.setRegionalId(codeDTO.getProvinceId()+codeDTO.getRegionId());
                            regionalDTO.setRegionalName(codeDTO.getRegionName());
                            dtos.add(regionalDTO);
                        }
                    }
                }

                listMap.put(String.valueOf(integer),dtos);
            }else{
                List<RegionalCodeDTO> regionals = regionalCodeDAO.getRegional(fideName, String.valueOf(integer));
                List<RedisRegionalDTO> dtos = new ArrayList<>();
                if(regionals.size() == 0){
                    RedisRegionalDTO regionalDTO = new RedisRegionalDTO();
                    regionalDTO.setRegionalId("-1");
                    regionalDTO.setRegionalName(String.valueOf(integer));
                    dtos.add(regionalDTO);
                }else{
                    for (RegionalCodeDTO codeDTO:regionals){
                        if(!String.valueOf(integer*1000).equals(codeDTO.getProvinceId()+codeDTO.getRegionId())){
                            RedisRegionalDTO regionalDTO = new RedisRegionalDTO();
                            regionalDTO.setRegionalId(codeDTO.getProvinceId()+codeDTO.getRegionId());
                            regionalDTO.setRegionalName(codeDTO.getRegionName());
                            dtos.add(regionalDTO);
                        }
                    }
                }
                listMap.put(String.valueOf(integer*1000),dtos);
            }
        }
        return listMap;
    }

    @Override
    public Map<String, List<RedisRegionalDTO>> regionalProvinceName(List<String> listId) {
        String fideName="provinceName";
        Map<String,List<RedisRegionalDTO>> listMap = new HashMap<>();
        for(String integer : listId){
                List<RegionalCodeDTO> regionals = regionalCodeDAO.getRegional(fideName,integer);
                List<RedisRegionalDTO> dtos = new ArrayList<>();
                if(regionals.size() == 0){
                    RedisRegionalDTO regionalDTO = new RedisRegionalDTO();
                    regionalDTO.setRegionalId("-1");
                    regionalDTO.setRegionalName(String.valueOf(integer));
                    dtos.add(regionalDTO);
                }else{
                    for (RegionalCodeDTO codeDTO:regionals){
                        if(!String.valueOf(integer).equals(codeDTO.getProvinceId()+codeDTO.getRegionId())){
                            RedisRegionalDTO regionalDTO = new RedisRegionalDTO();
                            regionalDTO.setRegionalId(codeDTO.getProvinceId()+codeDTO.getRegionId());
                            if("".equals(codeDTO.getRegionName())){
                                regionalDTO.setRegionalName(integer);
                            }else{
                                regionalDTO.setRegionalName(codeDTO.getRegionName());
                            }

                            dtos.add(regionalDTO);
                        }
                    }
                }
                listMap.put(integer,dtos);
            }
        return listMap;
    }

    @Override
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
                    RegionalCodeDTO regionalCodeDTO = new RegionalCodeDTO();
                    regionalCodeDTO.setProvinceId(codeDTO.getProvinceId()+"000");
                    regionalCodeDTO.setProvinceName(codeDTO.getProvinceName());
                    regionalCodeDTO.setRegionId(codeDTO.getProvinceId()+codeDTO.getRegionId());
                    regionalCodeDTO.setRegionName(codeDTO.getRegionName());
                    regionalCodeDTO.setStateId(codeDTO.getStateId()+"00000");
                    regionalCodeDTO.setStateName(codeDTO.getStateName());
                    dtos.add(regionalCodeDTO);
                }
            }

        }
        return dtos;
    }

    @Override
    public List<RegionalCodeDTO> getRegionalId(List<Integer> listId) {
        String fideName = "regionId";
        List<RegionalCodeDTO> dtos = new ArrayList<>();
        for(Integer dto:listId){
            List<RegionalCodeDTO> regionals = new ArrayList<>();
            if(dto < 1000){
                regionals = regionalCodeDAO.getRegional(fideName, String.valueOf(dto));
            }else if(dto < 10000){
                int i = dto%1000;
                regionals = regionalCodeDAO.getRegional(fideName, String.valueOf(i));
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
                    RegionalCodeDTO regionalCodeDTO = new RegionalCodeDTO();
                    regionalCodeDTO.setProvinceId(codeDTO.getProvinceId()+"000");
                    regionalCodeDTO.setProvinceName(codeDTO.getProvinceName());
                    regionalCodeDTO.setRegionId(codeDTO.getProvinceId()+codeDTO.getRegionId());
                    regionalCodeDTO.setRegionName(codeDTO.getRegionName());
                    regionalCodeDTO.setStateId(codeDTO.getStateId()+"00000");
                    regionalCodeDTO.setStateName(codeDTO.getStateName());
                    dtos.add(regionalCodeDTO);
                }
            }
        }
        return dtos;
    }
}
