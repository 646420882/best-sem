package com.perfect.admin.tests;

import com.alibaba.fastjson.JSON;
import com.perfect.admin.tests.base.JUnitBaseTest;
import com.perfect.param.Menu;
import com.perfect.param.UserMenuParams;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;

/**
 * Created by yousheng on 15/12/16.
 */
public class UserModuleControllerTest extends JUnitBaseTest {

    @Test
    public void testCreateUserModule() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/564ae6e70fa99e39902d2c79/modules").param("moduleid", "5671135777c8a5c9319deaa5"))
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }


    @Test
    public void testdeleteUserModule() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/564ae6e70fa99e39902d2c79/modules/5671135777c8a5c9319deaa5"))
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }

    @Test
    public void testGetUserModule() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/564ae6e70fa99e39902d2c79/modules"))
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }


    @Test
    public void testUpdateUserModuleMenu() throws Exception {

        UserMenuParams userMenuParams = new UserMenuParams();
        userMenuParams.setMenus(new ArrayList<>());

        Menu menu = new Menu().setId("1").setPid(null);
        menu.setSubMenus(new ArrayList<>());
        menu.getSubMenus().add("11");
        menu.getSubMenus().add("12");

        userMenuParams.getMenus().add(menu);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/564ae6e70fa99e39902d2c79/modules/5671135777c8a5c9319deaa5/submenus")
                .content(JSON.toJSONString(userMenuParams)).contentType("application/json;charset=UTF-8"))
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }
}
