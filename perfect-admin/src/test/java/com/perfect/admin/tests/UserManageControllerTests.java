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
