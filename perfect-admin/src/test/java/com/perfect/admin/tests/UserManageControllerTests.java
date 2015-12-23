package com.perfect.admin.tests;

import com.perfect.admin.tests.base.JUnitBaseTest;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Created by yousheng on 15/12/15.
 */
public class UserManageControllerTests extends JUnitBaseTest {

    @Test
    public void testListUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users")).andDo(MockMvcResultHandlers.print()).andReturn();
    }

    @Test
    public void testUpdateStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/" + sysUserid + "/status").param("status", "0")).andDo(MockMvcResultHandlers.print()).andReturn();
    }

    /**
     * 慎用!
     * @throws Exception
     */
    @Test
    public void testUpdatePassword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/" + sysUserid + "/password").param("password", "test1234")).andDo(MockMvcResultHandlers.print()).andReturn();
    }


    @Test
    public void testUpdatePayed() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/" + sysUserid + "/payed").param("payed", "true")).andDo(MockMvcResultHandlers.print()).andReturn();
    }


    @Test
    public void testUpdateAccountTime() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/" + sysUserid + "/time").param("start", "2015-12-23").param("end","2016-12-23")).andDo(MockMvcResultHandlers.print()).andReturn();
    }


    @Test
    public void testUpdateMenus() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/users/1/modules/souke/rights").contentType
                (MediaType.TEXT_PLAIN_VALUE)
                .param("menus", "1|2|3|")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();


        System.out.println("result = " + result);
    }
}
