package com.perfect.admin.tests;

import com.perfect.admin.tests.base.JUnitBaseTest;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

/**
 * Created by yousheng on 15/12/18.
 */
public class SystemLogControllerTest extends JUnitBaseTest {

    @Test
    public void testLogList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/syslogs")).andDo(MockMvcResultHandlers.print()).andReturn();
    }
}
