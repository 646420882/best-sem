package com.perfect.app.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Created by XiaoWei on 2014/10/29.
 */
public class XwControllerTest extends JUnitBaseController {
    @Test
    public void insertTest() throws Exception {
        String params="{\"tr\":\"测试行业\",\"cg\":\"测试行业类别\",\"gr\":\"分组1\",\"kw\":\"关键词1\",\"url\":\"www.baidu.com\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/person/saveTr").contentType(MediaType.APPLICATION_JSON).param("LexiconEntity",params)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}
