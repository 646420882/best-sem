package com.perfect.app.controller;

import com.perfect.core.AppContext;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Created by vbzer_000 on 2014/9/19.
 */
public class ElasticsearchControllerTest extends JUnitBaseController {

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
