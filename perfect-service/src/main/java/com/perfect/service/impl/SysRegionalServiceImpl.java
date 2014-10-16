package com.perfect.service.impl;

import com.perfect.dao.RegionalCodeDAO;
import com.perfect.dto.RedisRegionalDTO;
import com.perfect.dto.RegionalCodeDTO;
import com.perfect.service.SysRegionalService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

import static com.perfect.mongodb.utils.RegionalConstants.*;
/**
 * Created by SubDong on 2014/9/29.
 */
@Component("sysRegionalService")
public class SysRegionalServiceImpl implements SysRegionalService{

    @Resource
    private RegionalCodeDAO regionalCodeDAO;

    @Override
    public Map<String, List<RedisRegionalDTO>> regionalProvinceId(List<Integer> listId) {
        String fideName=FIDE_PROVINCEID;
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
        String fideName=FIDE_PROVINCENAME;
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
    public Map<String,List<RedisRegionalDTO>> getProvince() {
        String fideName = FIDE_REGIONID;
        String id = STRING_NUMBER_ZERO;
        List<RegionalCodeDTO> regionals = regionalCodeDAO.getRegional(fideName,id);
        Map<String,List<RedisRegionalDTO>> returnMap = new HashMap<>();
        List<RedisRegionalDTO> dtos = new ArrayList<>();
        for(RegionalCodeDTO codeDTO:regionals){
            if(!"00".equals(codeDTO.getProvinceId())){
                RedisRegionalDTO regionalDTO = new RedisRegionalDTO();
                regionalDTO.setRegionalId(codeDTO.getProvinceId());
                regionalDTO.setRegionalName(codeDTO.getProvinceName());
                regionalDTO.setShots(Integer.valueOf(codeDTO.getProvinceId()));
                dtos.add(regionalDTO);
            }
        }
        Collections.sort(dtos);
        returnMap.put(STRING_ROWS,dtos);

        return returnMap;
    }

    @Override
    public String getProvinceNameById(Integer provinceId) {
        return null;
    }

    @Override
    public String getRegionNameById(Integer regionId) {
        return null;
    }

    @Override
    public List<RegionalCodeDTO> getRegionalName(List<String> proName) {
        String fideName = FIDE_REGIONNAME;
        List<RegionalCodeDTO> dtos = new ArrayList<>();
        for(String dto:proName){
            List<RegionalCodeDTO> regionals = regionalCodeDAO.getRegional(fideName,dto);
            if(regionals.size() == 0){
                String fideName1 = FIDE_PROVINCENAME;
                List<RegionalCodeDTO> regionals1 = regionalCodeDAO.getRegional(fideName1,dto);
                if(regionals1.size() == 0){
                    RegionalCodeDTO regionalCodeDTO = new RegionalCodeDTO();
                    regionalCodeDTO.setRegionId("-1");
                    regionalCodeDTO.setRegionName(dto);
                    dtos.add(regionalCodeDTO);
                }else{
                    for(RegionalCodeDTO codeDTO1:regionals1){
                        if(!codeDTO1.getRegionId().equals(STRING_NUMBER_ALL)){
                            RegionalCodeDTO regionalCodeDTO = new RegionalCodeDTO();
                            regionalCodeDTO.setProvinceId(codeDTO1.getProvinceId()+STRING_NUMBER_ZERO);
                            regionalCodeDTO.setProvinceName(codeDTO1.getProvinceName());
                            regionalCodeDTO.setRegionId(codeDTO1.getProvinceId()+codeDTO1.getRegionId());
                            regionalCodeDTO.setRegionName(codeDTO1.getRegionName());
                            regionalCodeDTO.setStateId(codeDTO1.getStateId()+STRING_NUMBER_ZEROALL);
                            regionalCodeDTO.setStateName(codeDTO1.getStateName());
                            dtos.add(regionalCodeDTO);
                        }else{
                            RegionalCodeDTO regionalCodeDTO = new RegionalCodeDTO();
                            regionalCodeDTO.setProvinceId("");
                            regionalCodeDTO.setProvinceName("");
                            regionalCodeDTO.setRegionId("");
                            regionalCodeDTO.setRegionName("");
                            regionalCodeDTO.setStateId(codeDTO1.getStateId()+codeDTO1.getProvinceId()+codeDTO1.getRegionId());
                            regionalCodeDTO.setStateName(codeDTO1.getRegionName());
                            dtos.add(regionalCodeDTO);
                        }

                    }
                }
            }else{
                for(RegionalCodeDTO codeDTO:regionals){
                    if(!codeDTO.getRegionId().equals(STRING_NUMBER_ALL)){
                        RegionalCodeDTO regionalCodeDTO = new RegionalCodeDTO();
                        regionalCodeDTO.setProvinceId(codeDTO.getProvinceId()+STRING_NUMBER_ZERO);
                        regionalCodeDTO.setProvinceName(codeDTO.getProvinceName());
                        regionalCodeDTO.setRegionId(codeDTO.getProvinceId()+codeDTO.getRegionId());
                        regionalCodeDTO.setRegionName(codeDTO.getRegionName());
                        regionalCodeDTO.setStateId(codeDTO.getStateId()+STRING_NUMBER_ZEROALL);
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
    }

    @Override
    public List<RegionalCodeDTO> getRegionalId(List<Integer> listId) {
        String fideName = FIDE_REGIONID;
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
                regionals = regionalCodeDAO.getRegional(fideName, "".equals(regionid)?dto+"":String.valueOf(dto));
            }else if(dto < 100000){
                if(dto%1000 != 0){
                    String regionid = "";
                    int i = dto%1000;
                    if(i<10){
                        regionid = "00"+i;
                    }else if(i<100){
                        regionid = "0"+i;
                    }else{
                        regionid = i+"";
                    }
                    regionals = regionalCodeDAO.getRegional(fideName, "".equals(regionid)?i+"":String.valueOf(regionid));
                }
            }else if(dto >= 100000 && dto <= 10000000 ){
                if(dto%1000 != 0){
                    String regionid = "";
                    int i = dto%1000;
                    if(i<10){
                        regionid = "00"+i;
                    }else if(i<100){
                        regionid = "0"+i;
                    }
                    regionals = regionalCodeDAO.getRegional(fideName, "".equals(regionid)?i+"":String.valueOf(regionid));
                }
            }else{
                regionals = new ArrayList<>();
            }

            if(regionals.size() == 0){
                String fideName1 = FIDE_PROVINCEID;
                int i = dto/1000;
                List<RegionalCodeDTO> regionals1 = regionalCodeDAO.getRegional(fideName1, String.valueOf(i));
                if(regionals1.size() == 0){
                    RegionalCodeDTO regionalCodeDTO = new RegionalCodeDTO();
                    regionalCodeDTO.setRegionId("-1");
                    regionalCodeDTO.setRegionName(String.valueOf(dto));
                    dtos.add(regionalCodeDTO);
                }else{
                    for(RegionalCodeDTO codeDTO1:regionals1){
                        if(!codeDTO1.getRegionId().equals(STRING_NUMBER_ALL)){
                            RegionalCodeDTO regionalCodeDTO = new RegionalCodeDTO();
                            regionalCodeDTO.setProvinceId(codeDTO1.getProvinceId()+STRING_NUMBER_ZERO);
                            regionalCodeDTO.setProvinceName(codeDTO1.getProvinceName());
                            regionalCodeDTO.setRegionId(codeDTO1.getProvinceId()+codeDTO1.getRegionId());
                            regionalCodeDTO.setRegionName(codeDTO1.getRegionName());
                            regionalCodeDTO.setStateId(codeDTO1.getStateId()+STRING_NUMBER_ZEROALL);
                            regionalCodeDTO.setStateName(codeDTO1.getStateName());
                            dtos.add(regionalCodeDTO);
                        }else{
                            RegionalCodeDTO regionalCodeDTO = new RegionalCodeDTO();
                            regionalCodeDTO.setProvinceId("");
                            regionalCodeDTO.setProvinceName("");
                            regionalCodeDTO.setRegionId("");
                            regionalCodeDTO.setRegionName("");
                            regionalCodeDTO.setStateId(codeDTO1.getStateId()+codeDTO1.getProvinceId()+codeDTO1.getRegionId());
                            regionalCodeDTO.setStateName(codeDTO1.getRegionName());
                            dtos.add(regionalCodeDTO);
                        }

                    }
                }
            }else{
                for(RegionalCodeDTO codeDTO:regionals){
                if(!codeDTO.getRegionId().equals(STRING_NUMBER_ALL)){
                    RegionalCodeDTO regionalCodeDTO = new RegionalCodeDTO();
                    regionalCodeDTO.setProvinceId(codeDTO.getProvinceId()+STRING_NUMBER_ZERO);
                    regionalCodeDTO.setProvinceName(codeDTO.getProvinceName());
                    regionalCodeDTO.setRegionId(codeDTO.getProvinceId()+codeDTO.getRegionId());
                    regionalCodeDTO.setRegionName(codeDTO.getRegionName());
                    regionalCodeDTO.setStateId(codeDTO.getStateId()+STRING_NUMBER_ZEROALL);
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
    }



    public List<RegionalCodeDTO> getProvinceIdByRegionalId(List<Integer> listId){
        List<RegionalCodeDTO> dtos = new ArrayList<>();

        String fideName = FIDE_PROVINCEID;
        for(Integer dto:listId){
            dto = dto/1000%100;
          dtos.add(regionalCodeDAO.getRegionalByRegionId(fideName, dto+""));
        }
        return dtos;
    }


    /**
     * 对Map按key进行排序
     *
     * @param map
     * @return
     */
    private Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, String> sortMap = new TreeMap<>(new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    //Map,key比较器
    private class MapKeyComparator implements Comparator<String> {

        @Override
        public int compare(String str1, String str2) {
            return str1.compareTo(str2);
        }
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
