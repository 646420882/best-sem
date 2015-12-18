package com.perfect.admin.tests;

import com.alibaba.fastjson.JSON;
import com.perfect.admin.tests.base.JUnitBaseTest;
import com.perfect.dto.sys.SystemRoleDTO;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

/**
 * Created by yousheng on 15/12/17.
 */
public class SystemRoleControllerTest extends JUnitBaseTest {

    private String roleid = "56727d7e77c8243187c06982";

    @Test
    public void testListSystemRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sysroles")).andDo(MockMvcResultHandlers.print()).andReturn();
    }


    @Test
    public void testCreateAdminSystemRole() throws Exception {

        SystemRoleDTO systemRoleDTO = new SystemRoleDTO();
        systemRoleDTO.setPassword("yangle");
        systemRoleDTO.setName("杨乐");
        systemRoleDTO.setSuperAdmin(true);
        systemRoleDTO.setCtime(System.currentTimeMillis());
        systemRoleDTO.setContact("yangle@perfect-cn.cn");
        systemRoleDTO.setTitle("BOSS");
        systemRoleDTO.setLoginName("yangle");

        mockMvc.perform(MockMvcRequestBuilders.post("/sysroles").content(JSON.toJSONString(systemRoleDTO)).contentType("application/json;charset=UTF-8"))
                .andDo(MockMvcResultHandlers.print()).andReturn();

    }


    @Test
    public void testCreateSystemRole() throws Exception {

        String name = "zhangxueyou";

        SystemRoleDTO systemRoleDTO = new SystemRoleDTO();
        systemRoleDTO.setPassword(name);
        systemRoleDTO.setName("张学友");
        systemRoleDTO.setSuperAdmin(false);
        systemRoleDTO.setCtime(System.currentTimeMillis());
        systemRoleDTO.setContact(name + "@perfect-cn.cn");
        systemRoleDTO.setTitle("WORKER");
        systemRoleDTO.setLoginName(name);

        mockMvc.perform(MockMvcRequestBuilders.post("/sysroles").content(JSON.toJSONString(systemRoleDTO)).contentType("application/json;charset=UTF-8"))
                .andDo(MockMvcResultHandlers.print()).andReturn();

    }


    @Test
    public void testUpdateSystemRole() throws Exception {

        SystemRoleDTO systemRoleDTO = new SystemRoleDTO();
        systemRoleDTO.setPassword("yangle");
        systemRoleDTO.setName("杨乐");
        systemRoleDTO.setSuperAdmin(true);
        systemRoleDTO.setCtime(System.currentTimeMillis());
        systemRoleDTO.setContact("yangle@perfect-cn.cn");
        systemRoleDTO.setTitle("BOSS");
        systemRoleDTO.setLoginName("yangle");

        mockMvc.perform(MockMvcRequestBuilders.post("/sysroles/" + roleid).content(JSON.toJSONString(systemRoleDTO)).contentType("application/json;charset=UTF-8"))
                .andDo(MockMvcResultHandlers.print()).andReturn();

    }


    @Test
    public void testDeleteSystemRole() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/sysroles/" + roleid))
                .andDo(MockMvcResultHandlers.print()).andReturn();

    }
}
