package com.perfect.admin.tests.base;

import com.perfect.core.AppContext;
import com.perfect.core.SystemRoleInfo;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by IntelliJ IDEA 13.
 * User: baizz
 * Date: 2014-7-11
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
        @ContextConfiguration(name = "parent", locations = "classpath:spring-admin.xml"),
        @ContextConfiguration(name = "child", locations = "classpath:spring-admin-mvc.xml")
})
public class JUnitBaseTest {

    public String CONTENT_TYPE = "application/json;charset=UTF-8";

    public String userid = "567bc69177c8cee35a870c81";

    //百思测试
    public String sysUserid = "5678bf10279c4ba1e19bd431";

    public String roleid = "567b909177c891978cae112e";

    public String moduleid = "5678eaa476941d1cc5753f2f";

    public String usermoduleid = "5672534677c8b0c225a6f374";

    public String moduleMenuId = "56790191769474372653eb1b";

    public String updateModuleMenuId = "567251e177c843be6b69a8be";
    @Autowired
    private WebApplicationContext wac;
    protected MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

        SystemRoleInfo systemRoleInfo = new SystemRoleInfo();

        systemRoleInfo.setRoleName("yangle");
        systemRoleInfo.setRoleId(roleid);
        systemRoleInfo.setIsSuper(true);
        AppContext.setSystemUserInfo(systemRoleInfo);

        try {
            AppContext.setRemote(InetAddress.getLocalHost().toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
