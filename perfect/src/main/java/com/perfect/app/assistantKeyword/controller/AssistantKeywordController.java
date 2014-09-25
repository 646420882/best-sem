package com.perfect.app.assistantKeyword.controller;

import com.perfect.api.baidu.SearchTermsReport;
import com.perfect.autosdk.sms.v3.AttributeType;
import com.perfect.autosdk.sms.v3.RealTimeQueryResultType;
import com.perfect.core.AppContext;
import com.perfect.dto.CampaignTreeDTO;
import com.perfect.dto.KeywordDTO;
import com.perfect.dto.SearchwordReportDTO;
import com.perfect.entity.AdgroupEntity;
import com.perfect.entity.CampaignEntity;
import com.perfect.entity.KeywordEntity;
import com.perfect.mongodb.utils.PagerInfo;
import com.perfect.service.AssistantKeywordService;
import com.perfect.service.KeyWordBackUpService;
import com.perfect.utils.RegionalCodeUtils;
import com.perfect.utils.web.WebContext;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by john on 2014/8/14.
 */
@RestController
@Scope("prototype")
public class AssistantKeywordController {
    private static final String RES_SUCCESS = "success";

    @Resource
    private AssistantKeywordService assistantKeywordService;

    @Resource
    private KeyWordBackUpService keyWordBackUpService;

    @Resource
    private SearchTermsReport searchTermsReport;

    @Resource
    private WebContext webContext;


    /**
     * 批量添加关键词
     */
    @RequestMapping(value = "assistantKeyword/batchAdd",method = RequestMethod.POST , produces = "application/json")
    public void batchAddkeyword(@RequestBody List<KeywordDTO> list,HttpServletResponse response){
        assistantKeywordService.batchAddkeyword(list);
        webContext.writeJson(RES_SUCCESS,response);
    }



    /**
     * 批量修改关键词
     * @param response
     * @param keywords
     */
    @RequestMapping(value = "assistantKeyword/batchUpdate",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json")
    public  void batchUpdateKeyword(@RequestBody List<KeywordDTO> keywords,HttpServletResponse response){
        assistantKeywordService.batchUpdateKeyword(keywords);
        webContext.writeJson(RES_SUCCESS,response);
    }



    /**
     * 得到当前账户所有的关键词
     * @return
     */
    @RequestMapping(value = "assistantKeyword/list",method = {RequestMethod.GET,RequestMethod.POST})
    public void getAllKeywordList(HttpServletResponse response,String cid,String aid,Integer nowPage,Integer pageSize){
        PagerInfo page = assistantKeywordService.getKeyWords(cid,aid,nowPage,pageSize);
        webContext.writeJson(page,response);
    }

    /**
     * 根据一个或者多个关键词id删除关键词
     * @param kwids
     * @return
     */
    @RequestMapping(value = "assistantKeyword/deleteById" ,method = {RequestMethod.GET,RequestMethod.POST})
    public void deleteKeywordById(HttpServletResponse response,String kwids){
        String[] ids = kwids.split(",");
        assistantKeywordService.deleteByKwIds(Arrays.asList(ids));
        webContext.writeJson(RES_SUCCESS,response);
    }


    /**
     * 修改以下参数的信息
     * @param kwid
     * @param price
     * @param pcDestinationUrl
     * @param mobileDestinationUrl
     * @param matchType
     * @param phraseType
     * @param pause
     * @return
     */
    @RequestMapping(value = "assistantKeyword/edit",method = {RequestMethod.GET,RequestMethod.POST})
    public void updateKeywordName(HttpServletResponse response,String kwid,Double price,String pcDestinationUrl,String mobileDestinationUrl,Integer matchType,Integer phraseType,Boolean pause){
        String regex = "^\\d+$";

        KeywordEntity keywordEntity = new KeywordEntity();

        if(kwid.matches(regex)==true){
            keywordEntity.setKeywordId(Long.parseLong(kwid));
        }else{
            keywordEntity.setId(kwid);
        }
        keywordEntity.setPrice(price!=null?BigDecimal.valueOf(price):null);
        keywordEntity.setPcDestinationUrl(pcDestinationUrl);
        keywordEntity.setMobileDestinationUrl(mobileDestinationUrl);
        keywordEntity.setMatchType(matchType);
        keywordEntity.setPhraseType(phraseType);
        keywordEntity.setPause(pause);
        KeywordEntity keywordEntityRs = assistantKeywordService.updateKeyword(keywordEntity);
        webContext.writeJson(keywordEntityRs,response);
    }


    /**
     * 得到当前账户的推广计划的树形列表数据
     * @return
     */
    @RequestMapping(value = "assistantKeyword/campaignTree",method = {RequestMethod.GET,RequestMethod.POST})
    public void getCampaignTree(HttpServletResponse response){
        List<CampaignTreeDTO> treeList = assistantKeywordService.getCampaignTree(AppContext.getAccountId());
        webContext.writeJson(treeList,response);
    }

    /**
     *(选择计划和单元，只输入关键词名称的方式)
     * 根据用户的选择计划和单元，以及输入的关键词名称进行批量删除关键词
     * @param chooseInfos 用户选择的一个或多个计划和单元
     * @param keywordNames 用户输入的一个或多个的关键词名称
     * @return
     */
    @RequestMapping(value = "assistantKeyword/deleteByNameChoose",method = {RequestMethod.GET,RequestMethod.POST})
    public void deleteKeywordByNamesChoose(HttpServletResponse response,String chooseInfos,String keywordNames,Integer nowPage,Integer pageSize){
        Map<String,Object> map =  assistantKeywordService.validateDeleteKeywordByChoose(AppContext.getAccountId(), chooseInfos, keywordNames,nowPage,pageSize);
        webContext.writeJson(map,response);
    }



    /**（输入的方式)
     *根据用户输入的删除信息（计划名称，单元名称，关键词名称）批量删除关键词
     * @param deleteInfos 用户输入的一系列的计划名称，单元名称，关键词名称
     * @return
     */
    @RequestMapping(value = "assistantKeyword/validateDeleteByInput",method = {RequestMethod.GET,RequestMethod.POST})
    public void validateDeleteByInput(HttpServletResponse response,String deleteInfos){
        Map<String,Object> map = assistantKeywordService.validateDeleteByInput(AppContext.getAccountId(), deleteInfos);
        webContext.writeJson(map,response);
    }


    /**
     * （用户选择计划，单元，输入关键词的方式）
     * 将用户输入的关键词信息添加或更新到数据库
     * @param isReplace 是否将用户输入的信息替换该单元下相应的内容
     * @param chooseInfos 用户选择的推广计划和推广单元
     * @param keywordInfos 用户输入的多个关键词信息
     * @return
     */
    @RequestMapping(value = "assistantKeyword/addOrUpdateKeywordByChoose",method = {RequestMethod.GET,RequestMethod.POST})
    public void batchAddOrUpdateKeywordByChoose(HttpServletResponse response,Boolean isReplace,String chooseInfos,String keywordInfos){
        Map<String,Object> map = assistantKeywordService.batchAddOrUpdateKeywordByChoose(AppContext.getAccountId(),isReplace,chooseInfos,keywordInfos);
        webContext.writeJson(map,response);
    }


    //未完成
    /**
     * （输入的方式）
     * 将用户输入的关键词信息添加或更新到数据库
     * @param isReplace 是否将用户输入的信息替换该单元下相应的内容
     * @param keywordInfos 用户输入的多个关键词信息
     * @return
     */
    /*@RequestMapping(value = "assistantKeyword/addOrUpdateKeywordByInput",method = {RequestMethod.GET,RequestMethod.POST})
       public ModelAndView batchAddOrUpdateKeywordByInput(Boolean isReplace,String keywordInfos){
        assistantKeywordService.batchAddOrUpdateKeywordByInput(currentAccountId,isReplace,keywordInfos);
        return new ModelAndView();
    }*/


    /**
     * 显示批量删除弹出窗口
     * @return
     */
    @RequestMapping(value = "assistantKeyword/showBatchDelDialog",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView showBatchDelDialog(){
        return new ModelAndView("promotionAssistant/alert/batchDelKeyword");
    }


    /**
     * 显示批量添加更新弹出窗口
     * @return
     */
    @RequestMapping(value = "assistantKeyword/showAddOrUpdateKeywordDialog",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView showAddOrUpdateKeywordDialog(){
        return new ModelAndView("promotionAssistant/alert/addOrUpdateKeyword");
    }


    /**
     * 显示搜索词报告弹出窗口
     * @return
     */
    @RequestMapping(value = "assistantKeyword/showSearchWordDialog",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView showSearchWordDialog(){
        return new ModelAndView("promotionAssistant/alert/searchwordReport");
    }



    /**
     * 还原新增的关键词
     * @param response
     * @param id
     */
    @RequestMapping(value = "assistantKeyword/reducAdd",method = {RequestMethod.GET,RequestMethod.POST})
    public void reducAdd(HttpServletResponse response,String id){
        assistantKeywordService.deleteByKwIds(Arrays.asList(new String[]{id}));
        webContext.writeJson(RES_SUCCESS,response);
    }


    /**
     * 还原修改的关键词
     * @param response
     * @param id
     */
    @RequestMapping(value = "assistantKeyword/reducUpdate",method = {RequestMethod.GET,RequestMethod.POST})
    public void reducUpdate(HttpServletResponse response,String id){
        KeywordEntity keywordEntity = keyWordBackUpService.reducUpdate(id);
        webContext.writeJson(keywordEntity,response);
    }

    /**
     * 还原功能软删除
     * @param response
     * @param id
     */
    @RequestMapping(value = "assistantKeyword/reducDel",method = {RequestMethod.GET,RequestMethod.POST})
    public void reducDel(HttpServletResponse response,String id){
        keyWordBackUpService.reducDel(id);
        webContext.writeJson(RES_SUCCESS,response);
    }


    /**
     * 根据账户id得到计划
     */
    @RequestMapping(value = "assistantKeyword/getCampaignByAccountId",method = {RequestMethod.GET,RequestMethod.POST})
    public void getCampaignByAccountId(HttpServletResponse response){
        Iterable<CampaignEntity> list = assistantKeywordService.getCampaignByAccountId();
        webContext.writeJson(list,response);
    }

    /**
     * 根据计划cid得到单元
     */
    @RequestMapping(value = "assistantKeyword/getAdgroupByCid",method = {RequestMethod.GET,RequestMethod.POST})
    public void getAdgroupByCid(HttpServletResponse response,String cid){
        Iterable<AdgroupEntity> list = assistantKeywordService.getAdgroupByCid(cid);
        webContext.writeJson(list,response);
    }


    /**
     * 从百度上获取搜索词报告
     */
    @RequestMapping(value = "assistantKeyword/getSearchWordReport",method = {RequestMethod.GET,RequestMethod.POST})
    public void getSearchWordReport(HttpServletResponse response,Integer levelOfDetails,String startDate,String endDate,String attributes,Integer device,Integer searchType){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        List<AttributeType> list = null;

        if(attributes!=null&&attributes!=""){
            list = new ArrayList<>();
            String[] attrs = attributes.split(",");
            Map<Integer, String> regions = RegionalCodeUtils.regionalCodeName(Arrays.asList(attrs));
            AttributeType attributeType = new AttributeType();
            attributeType.setKey("provid");
            attributeType.setValue(new ArrayList<Integer>(regions.keySet()));
            list.add(attributeType);
        }

        List<RealTimeQueryResultType> resultList = null;
        try {
            resultList = searchTermsReport.getSearchTermsReprot(levelOfDetails, df.parse(startDate), df.parse(endDate), list, device, searchType);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<SearchwordReportDTO> dtoList = new ArrayList<>();
       for(RealTimeQueryResultType resultType:resultList){
           SearchwordReportDTO searchwordReportDTO = new SearchwordReportDTO();
           searchwordReportDTO.setKeyword(resultType.getQueryInfo(3));
           searchwordReportDTO.setSearchWord(resultType.getQuery());
           searchwordReportDTO.setClick(resultType.getKPI(0));
           searchwordReportDTO.setImpression(resultType.getKPI(1));
           searchwordReportDTO.setSearchEngine(resultType.getQueryInfo(8));
           searchwordReportDTO.setAdgroupName(resultType.getQueryInfo(2));
           searchwordReportDTO.setCampaignName(resultType.getQueryInfo(1));
           searchwordReportDTO.setCreateTitle(resultType.getQueryInfo(4));
           searchwordReportDTO.setCreateDesc1(resultType.getQueryInfo(5));
           searchwordReportDTO.setCreateDesc2(resultType.getQueryInfo(6));
           searchwordReportDTO.setDate(resultType.getDate());
           searchwordReportDTO.setParseExtent(resultType.getQueryInfo(9));
           dtoList.add(searchwordReportDTO);
       }


        webContext.writeJson(dtoList,response);
    }


    /**
     * 将搜索词报告中关键词添加到现登录的账户
     */
    @RequestMapping(value = "assistantKeyword/addSearchwordKeyword",method = {RequestMethod.GET,RequestMethod.POST})
    public void addSearchwordKeyword(HttpServletResponse response,String agid,String keywords,String matchType){
        String[] keywordArray = keywords.split("\n");

        List<KeywordEntity> list = new ArrayList<>();
        for(String kwd:keywordArray){
            KeywordEntity keywordEntity = new KeywordEntity();
            keywordEntity.setKeyword(kwd);
            if(agid.matches("^\\d+$")){
                keywordEntity.setAdgroupId(Long.parseLong(agid));
             }else{
                keywordEntity.setAdgroupObjId(agid);
            }
            String[] match = matchType.split("-");
            if(match.length==1){
                keywordEntity.setMatchType(Integer.parseInt(match[0]));
            }else{
                keywordEntity.setMatchType(Integer.parseInt(match[0]));
                keywordEntity.setPhraseType(Integer.parseInt(match[1]));
            }
            keywordEntity.setAccountId(AppContext.getAccountId());
            keywordEntity.setPause(false);
            keywordEntity.setStatus(-1);
            keywordEntity.setLocalStatus(1);
            list.add(keywordEntity);
        }
        assistantKeywordService.saveSearchwordKeyword(list);
        webContext.writeJson(RES_SUCCESS,response);
    }

    /**
     * 将搜索词报告中关键词添加到现登录的账户
     */
    @RequestMapping(value = "assistantKeyword/setNeigWord",method = {RequestMethod.GET,RequestMethod.POST})
    public void setNeigWord(HttpServletResponse response,String agid,String keywords,Integer neigType){
        assistantKeywordService.setNeigWord(agid,keywords,neigType);
        webContext.writeJson(RES_SUCCESS,response);
    }







}
