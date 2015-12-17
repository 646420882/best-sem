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
                "sem.best-ad.cn").param("modulename", "百思搜客3")).andDo(MockMvcResultHandlers.print()).andReturn();
    }

    @Test
    public void testUpdateModule() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/sysmodules/" + moduleid).param("moduleurl",
                "hy2.best-ad.cn").param("modulename", "百思慧眼1")).andDo(MockMvcResultHandlers.print()).andReturn();
    }

    @Test
    public void testList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sysmodules"))
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }


    @Test
    public void testListByName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sysmodules/" + moduleid))
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }


    @Test
    public void testCreateMenu() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/sysmodules/" + moduleid + "/menus").param("menuname",
                "账户概览3").param("order", "1"))
                .andDo(MockMvcResultHandlers.print()).andReturn();

    }


    @Test
    public void testDeleteMenu() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/sysmodules/" + moduleid + "/menus/" + moduleMenuId))
                .andDo(MockMvcResultHandlers.print()).andReturn();

    }


    @Test
    public void testUpdateMenu() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post
                ("/sysmodules/" + moduleid + "/menus/" + updateModuleMenuId).param("menuname", "推广助手").param("order", "100"))
                .andDo(MockMvcResultHandlers.print()).andReturn();

    }
}
