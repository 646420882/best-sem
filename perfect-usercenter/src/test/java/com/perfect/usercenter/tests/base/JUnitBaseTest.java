package com.perfect.usercenter.tests.base;

import com.perfect.core.AppContext;
import com.perfect.core.SystemUserInfo;
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

    public String userid = "53fec848e4b07cfcbba06db6";

    public String roleid = "56727d7e77c8243187c06982";

    public String moduleid = "5670f8ac77c8f56e10489559";

    public String usermoduleid = "5672534677c8b0c225a6f374";

    public String moduleMenuId = "5672517477c8d7f4989df0f0";

    public String updateModuleMenuId = "567251e177c843be6b69a8be";
    @Autowired
    private WebApplicationContext wac;
    protected MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

        SystemUserInfo systemUserInfo = new SystemUserInfo();

        systemUserInfo.setUser("yangle");

        systemUserInfo.setIsSuper(true);
        AppContext.setSystemUserInfo(systemUserInfo);

        try {
            AppContext.setRemote(InetAddress.getLocalHost().toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
