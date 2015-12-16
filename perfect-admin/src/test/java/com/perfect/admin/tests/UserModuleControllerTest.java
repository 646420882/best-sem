package com.perfect.admin.tests;

import com.perfect.admin.tests.base.JUnitBaseTest;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

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
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/564ae6e70fa99e39902d2c79/modules").param("moduleid", "5671135777c8a5c9319deaa5"))
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }

    @Test
    public void testGetUserModule() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/564ae6e70fa99e39902d2c79/modules"))
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }
}
