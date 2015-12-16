package com.perfect.admin.tests;

import com.perfect.admin.tests.base.JUnitBaseTest;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

/**
 * Created by yousheng on 15/12/16.
 */
public class SystemModuleControllerTest extends JUnitBaseTest {


    @Test
    public void testCreateModule() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/sysmodules").param("moduleurl",
                "sem" +
                        ".best-ad.cn").param("modulename", "百思搜客3"))
                .andDo
                        (MockMvcResultHandlers.print()).andReturn();
    }

    @Test
    public void testUpdateModule() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/sysmodules/5670f8ac77c8f56e10489559").param("moduleurl",
                "hy1.best-ad.cn").param("modulename", "百思慧眼1"))
                .andDo
                        (MockMvcResultHandlers.print()).andReturn();
    }

    @Test
    public void testList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sysmodules"))
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }


    @Test
    public void testListByName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sysmodules/5670f8ac77c8f56e10489559"))
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }


    @Test
    public void testCreateMenu() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/sysmodules/5671135777c8a5c9319deaa5/menus").param("menuname",
                "账户概览4").param("order", "1"))
                .andDo(MockMvcResultHandlers.print()).andReturn();

    }


    @Test
    public void testDeleteMenu() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/sysmodules/5670f8ac77c8f56e10489559/menus/5670f8db77c8ea5448e385f3"))
                .andDo(MockMvcResultHandlers.print()).andReturn();

    }


    @Test
    public void testUpdateMenu() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post
                ("/sysmodules/5670f8ac77c8f56e10489559/menus/5670fbe077c8269ac8f43d48").param("menuname", "推广助手"))
                .andDo(MockMvcResultHandlers.print()).andReturn();

    }
}
