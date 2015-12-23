package com.perfect.usercenter.tests.base.usercenter;

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

/**
 * Created on 2015-12-22.
 *
 * @author dolphineor
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
        @ContextConfiguration(name = "parent", locations = "classpath:usercenter-context.xml"),
        @ContextConfiguration(name = "child", locations = "classpath:usercenter-context-mvc.xml"),
        @ContextConfiguration(name = "child", locations = "classpath:usercenter-mongodb.xml")
})
public class JUnitBaseTest {

    @Autowired
    private WebApplicationContext wac;
    protected MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
}
