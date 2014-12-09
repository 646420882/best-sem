package com.perfect.test.app.controller;

import com.perfect.service.KeywordService;
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
public class KeywordControllerTest extends JUnitBaseControllerTest {

    @Autowired
    private KeywordService keywordService;

    public void setKeywordService(@Qualifier("keywordService") KeywordService keywordService) {
        this.keywordService = keywordService;
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
    /*@Resource
    SystemUserDAO systemUserDAO;
    @Test
    public void test() throws Exception {
        AsynchronousReportDAO dao = new AsynchronousReportDAOImpl(systemUserDAO);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

        List<String> list = DateUtils.getPeriod(yesterday, yesterday);
        for (String dateStr : list) {
            dao.getAccountReportData(dateStr);
            dao.getCampaignReportData(dateStr);
            dao.getAdgroupReportData(dateStr);
            dao.getCreativeReportData(dateStr);
            dao.getKeywordReportData(dateStr);
            dao.getRegionReportData(dateStr);
        }
    }*/

}
