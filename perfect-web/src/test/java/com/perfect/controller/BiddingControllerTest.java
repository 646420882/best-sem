package com.perfect.controller;

import com.perfect.base.JUnitBaseTest;
import com.perfect.core.AppContext;
import com.perfect.dao.bidding.BiddingRuleDAO;
import com.perfect.service.BiddingRuleService;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by vbzer_000 on 2014/8/26.
 */
public class BiddingControllerTest extends JUnitBaseTest {

    @Resource
    private BiddingRuleDAO biddingRuleDAO;


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
    public void testBiding(){
//        Map<String, List<String>> map = biddingRuleDAO.getAllEnableRules("jiehun");
        System.out.println();
    }


    @Test
    public void save() throws Exception {
//        AppContext.setUser("perfect");
//
//        BiddingRuleEntity entity = new BiddingRuleEntity();
//
////        JSONUtils.getJsonObject()
//
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/bidding/save").contentType(MediaType.APPLICATION_JSON).content("{" +
//                "  \"kwid\" : 906068486,\n" +
//                "  \"kw\" : \"深圳南山 买房\",\n" +
//                "  \"stgy\" : {\n" +
//                "    \"bdtype\" : 0,\n" +
//                "    \"type\" : 0,\n" +
//                "    \"max\" : 5.0,\n" +
//                "    \"min\" : 1.0,\n" +
//                "    \"spd\" : 101,\n" +
//                "    \"intval\" : 30,\n" +
//                "    \"pstra\" : 1,\n" +
//                "    \"failed\" : 0,\n" +
//                "    \"pos\" : 0\n" +
//                "  },\n" +
//                "  \"cp\" : 1.0,\n" +
//                "  \"next\" : NumberLong(1),\n" +
//                "  \"priority\" : 0,\n" +
//                "  \"ebl\" : true,\n" +
//                "  \"aid\" : NumberLong(7001963)\n" +
//                "}").accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.content()
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
    }
}
