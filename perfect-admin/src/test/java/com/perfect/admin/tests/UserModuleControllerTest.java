package com.perfect.admin.tests;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.perfect.admin.tests.base.JUnitBaseTest;
import com.perfect.dto.sys.ModuleAccountInfoDTO;
import com.perfect.dto.sys.SystemMenuDTO;
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
    public void testdeleteUserModule() throws Exception {
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

}
