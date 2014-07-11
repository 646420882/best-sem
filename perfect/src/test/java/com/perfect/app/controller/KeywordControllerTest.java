package com.perfect.app.controller;

import com.perfect.dao.KeywordDAO;
import com.perfect.entity.KeywordEntity;
import com.perfect.utils.JSONUtils;
import net.sf.json.JSONArray;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Created by baizz on 2014-7-11.
 */
public class KeywordControllerTest extends JUnitBaseController {

    @Autowired
    private KeywordDAO keywordDAO;

    public void setKeywordDAO(@Qualifier("keywordDAO") KeywordDAO keywordDAO) {
        this.keywordDAO = keywordDAO;
    }

    @Test
    public void getKeywordByAdgroupId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/keyword/getKeywordByAdgroupId/536421136").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getKeywordIdByAdgroupId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/keyword/getKeywordIdByAdgroupId/536421136").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void findByPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/keyword/showAll").contentType(MediaType.APPLICATION_JSON).param("skip", "0").param("limit", "5").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getKeywordByKeywordId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/keyword/getKeywordByKeywordId/566590590").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void addKeyword() throws Exception {
        KeywordEntity keywordEntity = new KeywordEntity();
        keywordEntity.setKeywordId(566590591l);
        keywordEntity.setKeyword("北京婚博会官网");
        keywordEntity.setAdgroupId(536421136l);
        keywordEntity.setPrice(1.d);
        JSONArray jsonArray = JSONUtils.getJSONArrayData(new KeywordEntity[]{keywordEntity});
        mockMvc.perform(MockMvcRequestBuilders.post("/keyword/add").contentType(MediaType.APPLICATION_JSON).content(jsonArray.toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void updateKeyword() throws Exception {
        String requestParam = "{\"keywordId\" : " + 566590591l + ", \"price\" : " + 1.5d + "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/keyword/566590591/update").contentType(MediaType.APPLICATION_JSON).param("keywordEntity", requestParam).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void updateMultiKeyword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/keyword/update/price/5").contentType(MediaType.APPLICATION_JSON).param("seedWord", "北京").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void deleteKeywordById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/keyword/566590591/del").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void deleteKeywordByIds() throws Exception {
        Long[] keywordIds = {566590590l, 566590591l};
        JSONArray jsonArray = JSONUtils.getJSONArrayData(keywordIds);
        mockMvc.perform(MockMvcRequestBuilders.post("/keyword/deleteMulti").contentType(MediaType.APPLICATION_JSON).content(jsonArray.toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
