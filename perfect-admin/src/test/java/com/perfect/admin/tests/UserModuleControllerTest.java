package com.perfect.admin.tests;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.perfect.admin.tests.base.JUnitBaseTest;
import com.perfect.dto.sys.ModuleAccountInfoDTO;
import com.perfect.dto.sys.SystemMenuDTO;
import com.perfect.dto.sys.UserModuleMenuDTO;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

/**
 * Created by yousheng on 15/12/16.
 */
public class UserModuleControllerTest extends JUnitBaseTest {

    @Test
    public void testCreateUserModule() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/" + userid + "/modules").param("moduleid", moduleid))
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }


    @Test
    public void testDeleteUserModule() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/" + userid + "/modules/" + usermoduleid))
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }

    @Test
    public void testGetUserModule() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/" + userid + "/modules"))
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }


    @Test
    public void testUpdateUserModuleMenu() throws Exception {

        SystemMenuDTO userMenusDTO = new SystemMenuDTO();
        userMenusDTO.setMenuId("5672519077c802f978f3e1e6");
//        userMenusDTO.setSubMenus(Lists.newArrayList(new SystemMenuDTO().setMenuid("5670fbe077c8269ac8f43d49"), new SystemMenuDTO().setMenuid("5670fbe077c8269ac8f43d50")));

        List<SystemMenuDTO> systemMenuDTOs = Lists.newArrayList(userMenusDTO);


        userMenusDTO = new SystemMenuDTO();

        userMenusDTO.setMenuId("567251a077c80233f5ba30d5");
//        userMenusDTO.setSubMenus(Lists.newArrayList(new SystemMenuDTO().setMenuid("2-1"), new SystemMenuDTO().setMenuid("2-2")));

        systemMenuDTOs.add(userMenusDTO);


        mockMvc.perform(MockMvcRequestBuilders.post("/users/" + userid + "/modules/" + usermoduleid + "/submenus")
                .content(JSON.toJSONString(systemMenuDTOs)).contentType("application/json;charset=UTF-8"))
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }


    @Test
    public void testGetUserMenus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/" + userid + "/modules/" + usermoduleid + "/submenus"))
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }


    @Test
    public void testCreateModuleAccount() throws Exception {
        ModuleAccountInfoDTO moduleAccountInfoDTO = new ModuleAccountInfoDTO();

        moduleAccountInfoDTO.setBaiduUserName("perfect-xxxxxxxxxx");
        moduleAccountInfoDTO.setBaiduPassword("dsadasdasdsadas");
        moduleAccountInfoDTO.setRegDomain("http://www.best-ad.cn");


        mockMvc.perform(MockMvcRequestBuilders.post("/users/" + userid + "/modules/" + usermoduleid + "/accounts")
                .content(JSON.toJSONString(moduleAccountInfoDTO)).contentType("application/json;charset=UTF-8"))
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }


    @Test
    public void testUpdateUserMenus() throws Exception {
        UserModuleMenuDTO userModuleMenuDTO = new UserModuleMenuDTO();

        userModuleMenuDTO.setModuleName("百思慧眼");
        userModuleMenuDTO.setModuleUrl("hy.best-ad.cn");


        List<String> menus = Lists.newArrayList();

        menus.add("网站概览");
        menus.add("趋向分析|实时访客");
        menus.add("趋向分析|今日统计");
        menus.add("趋向分析|昨日统计");
        menus.add("趋向分析|最近30天");
        menus.add("来源分析|全部来源");
        menus.add("来源分析|搜索引擎");
        menus.add("来源分析|搜索词");
        menus.add("来源分析|外部链接");
        menus.add("来源分析|来源变化榜");
        menus.add("页面分析|受访页面");
        menus.add("页面分析|入口页面");
        menus.add("页面分析|页面热点图");
        menus.add("访客分析|访客地图");
        menus.add("访客分析|设备环境");
        menus.add("访客分析|新老访客");
        menus.add("价值透析|流量地图");
        menus.add("价值透析|频道流转");
        menus.add("转化分析");
        menus.add("指定广告跟踪");
        menus.add("同类群组分析");


        userModuleMenuDTO.setMenus(menus);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/" + userid + "/menus").content(JSON.toJSONString(userModuleMenuDTO)).contentType(CONTENT_TYPE))
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }

    @Test
    public void testGetUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/" + userid)).andDo(MockMvcResultHandlers.print()).andReturn();
    }

}
