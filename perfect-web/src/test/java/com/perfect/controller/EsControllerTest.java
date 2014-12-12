package com.perfect.controller;

import com.perfect.base.JUnitBaseTest;
import com.perfect.core.AppContext;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

/**
 * Created by vbzer_000 on 2014/9/19.
 */
public class EsControllerTest extends JUnitBaseTest {

    @Test
    public void test() throws Exception {
        AppContext.setUser("perfect");
        mockMvc.perform(MockMvcRequestBuilders.post("/creative/q").contentType(MediaType.APPLICATION_JSON)
                .param("q", "帆摄影").param("page", "1").param("size", "10")
                .accept(MediaType.APPLICATION_JSON))

                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
