package com.perfect.app.controller;

import com.perfect.core.AppContext;
import com.perfect.entity.bidding.BiddingRuleEntity;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Created by vbzer_000 on 2014/8/26.
 */
public class BiddingControllerTest extends JUnitBaseController {


    @Test
    public void test() throws Exception {
        AppContext.setUser("perfect");
        mockMvc.perform(MockMvcRequestBuilders.post("/bidding/rank").contentType(MediaType.APPLICATION_JSON)
                .param("acid", "6243012").param("ids", "4014204784").param("cid", "10556151")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


    @Test
    public void save() throws Exception {
        AppContext.setUser("perfect");

        BiddingRuleEntity entity = new BiddingRuleEntity();

//        JSONUtils.getJsonObject()


        mockMvc.perform(MockMvcRequestBuilders.post("/bidding/save").contentType(MediaType.APPLICATION_JSON).content("{" +
                "  \"kwid\" : 906068486,\n" +
                "  \"kw\" : \"深圳南山 买房\",\n" +
                "  \"stgy\" : {\n" +
                "    \"bdtype\" : 0,\n" +
                "    \"type\" : 0,\n" +
                "    \"max\" : 5.0,\n" +
                "    \"min\" : 1.0,\n" +
                "    \"spd\" : 101,\n" +
                "    \"intval\" : 30,\n" +
                "    \"pstra\" : 1,\n" +
                "    \"failed\" : 0,\n" +
                "    \"pos\" : 0\n" +
                "  },\n" +
                "  \"cp\" : 1.0,\n" +
                "  \"next\" : NumberLong(1),\n" +
                "  \"priority\" : 0,\n" +
                "  \"ebl\" : true,\n" +
                "  \"aid\" : NumberLong(7001963)\n" +
                "}").accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.content()
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
